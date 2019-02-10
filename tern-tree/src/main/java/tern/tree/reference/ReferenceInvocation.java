package tern.tree.reference;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.error.InternalStateException;
import tern.core.function.Invocation;
import tern.core.function.InvocationCache;
import tern.core.function.bind.FunctionBinder;
import tern.core.function.bind.FunctionMatcher;
import tern.core.function.dispatch.FunctionDispatcher;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceEvaluation;
import tern.core.trace.TraceInterceptor;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;
import tern.core.variable.Value;
import tern.tree.ArgumentList;
import tern.tree.ModifierAccessVerifier;
import tern.tree.NameReference;
import tern.tree.constraint.GenericList;
import tern.tree.constraint.GenericParameterExtractor;
import tern.tree.literal.TextLiteral;

public class ReferenceInvocation implements Compilation {

   private final Evaluation[] evaluations;
   private final NameReference identifier;
   private final ArgumentList arguments;
   private final GenericList generics;
   
   public ReferenceInvocation(TextLiteral identifier, GenericList generics, ArgumentList arguments, Evaluation... evaluations) {
      this.identifier = new NameReference(identifier);
      this.evaluations = evaluations;
      this.arguments = arguments;
      this.generics = generics;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();     
      Trace trace = Trace.getInvoke(module, path, line);
      Evaluation invocation = create(module, path, line);
      
      return new TraceEvaluation(interceptor, invocation, trace);
   }
   
   private Evaluation create(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      String name = identifier.getName(scope);
      TypeExtractor extractor = context.getExtractor();
      FunctionBinder binder = context.getBinder();   
      FunctionMatcher matcher = binder.bind(name);
      
      return new CompileResult(matcher, extractor, generics, arguments, evaluations, name);     
   }
   
   private static class CompileResult extends Evaluation {
   
      private final GenericParameterExtractor extractor;
      private final ModifierAccessVerifier verifier;
      private final Evaluation[] evaluations; // func()[1][x]
      private final FunctionMatcher matcher;
      private final ArgumentList arguments;
      private final InvocationCache cache;
      private final String name;
      
      public CompileResult(FunctionMatcher matcher, TypeExtractor extractor, GenericList generics, ArgumentList arguments, Evaluation[] evaluations, String name) {
         this.extractor = new GenericParameterExtractor(generics);
         this.cache = new InvocationCache(matcher, extractor);
         this.verifier = new ModifierAccessVerifier();
         this.evaluations = evaluations;
         this.arguments = arguments;
         this.matcher = matcher;
         this.name = name;
      }
      
      @Override
      public void define(Scope scope) throws Exception { 
         arguments.define(scope);
         
         for(Evaluation evaluation : evaluations) {
            evaluation.define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         Type type = left.getType(scope);         
         Type[] array = arguments.compile(scope); 
         Scope composite = extractor.extract(scope);
         FunctionDispatcher dispatcher = matcher.match(composite, left);
         Constraint result = dispatcher.compile(composite, left, array);

         if(result.isPrivate()) {
            Module module = scope.getModule();
            Context context = module.getContext();
            ErrorHandler handler = context.getHandler();
            
            if(!verifier.isAccessible(composite, type)) {
               handler.failCompileAccess(composite, type, name, array);
            }
         }
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            result = evaluation.compile(composite, result);
         }
         return result; 
      }

      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Object[] array = arguments.create(scope); 
         Invocation connection = cache.fetch(scope, left, array);
         Object object = connection.invoke(scope, left, array);
         Value value = Value.getTransient(object);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            value = evaluation.evaluate(scope, value);
         }
         return value; 
      }
   }
}