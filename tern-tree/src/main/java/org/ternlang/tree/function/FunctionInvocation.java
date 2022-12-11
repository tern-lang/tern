package org.ternlang.tree.function;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationCache;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.bind.FunctionMatcher;
import org.ternlang.core.function.dispatch.FunctionDispatcher;
import org.ternlang.core.link.ImplicitImportLoader;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.LocalValueFinder;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceEvaluation;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.variable.Constant;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.ArgumentList;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.NameReference;
import org.ternlang.tree.PlaceHolder;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.closure.Closure;
import org.ternlang.tree.closure.ClosureParameterList;
import org.ternlang.tree.constraint.GenericList;
import org.ternlang.tree.constraint.GenericParameterExtractor;
import org.ternlang.tree.literal.TextLiteral;
import org.ternlang.tree.reference.GenericArgumentList;

import java.util.concurrent.atomic.AtomicReference;

import static org.ternlang.core.Reserved.PLACE_HOLDER;
import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.variable.Value.NULL;

public class FunctionInvocation implements Compilation {

   private final Evaluation[] evaluations;
   private final NameReference identifier;
   private final ArgumentList arguments;
   private final GenericList generics;
   
   public FunctionInvocation(TextLiteral identifier, GenericList generics, ArgumentList arguments, Evaluation... evaluations) {
      this.identifier = new NameReference(identifier);
      this.evaluations = evaluations;
      this.arguments = arguments;
      this.generics = generics;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Evaluation evaluation = build(module, path, line);
      Scope scope = module.getScope();

      if(arguments.expansion(scope)) {
         StringToken token = new StringToken(PLACE_HOLDER);
         PlaceHolder holder = new PlaceHolder(token);
         ModifierList modifiers = new ModifierList();
         GenericList generics = new GenericArgumentList();
         AnnotationList annotations = new AnnotationList();
         ParameterDeclaration declaration = new ParameterDeclaration(annotations, modifiers, holder);
         ClosureParameterList parameters = new ClosureParameterList(declaration);
         Closure closure = new Closure(modifiers, generics, parameters, evaluation);

         return closure.compile(module, path, line);
      }
      return evaluation;
   }

   private Evaluation build(Module module, Path path, int line) throws Exception {
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
      private final AtomicReference<Address> location; 
      private final Evaluation[] evaluations; // func()[1][x]
      private final ImplicitImportLoader loader;
      private final LocalValueFinder finder;
      private final FunctionMatcher matcher;
      private final ArgumentList arguments;
      private final InvocationCache cache;   
      private final String name;
      
      public CompileResult(FunctionMatcher matcher, TypeExtractor extractor, GenericList generics, ArgumentList arguments, Evaluation[] evaluations, String name) {
         this.extractor = new GenericParameterExtractor(generics);
         this.cache = new InvocationCache(matcher, extractor);
         this.location = new AtomicReference<Address>();
         this.loader = new ImplicitImportLoader();
         this.finder = new LocalValueFinder(name);
         this.evaluations = evaluations;
         this.arguments = arguments;
         this.matcher = matcher;
         this.name = name;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         ScopeIndex index = scope.getIndex();
         Address address = index.get(name);

         if(address == null) {
            loader.loadImports(scope, name);
         } else {
            location.set(address);
         }
         arguments.define(scope);
         
         for(Evaluation evaluation : evaluations) {
            evaluation.define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         Address address = location.get();
         Value value = finder.findFunction(scope, address);
 
         if(value != null) { 
            Constraint constraint = value.getConstraint();
            Type type = constraint.getType(scope);

            if(type == null) {
               arguments.compile(scope); 
               return NONE;
            }
            return compile(scope, name, constraint);            
         }
         return compile(scope, name);         
      }
      
      private Constraint compile(Scope scope, String name) throws Exception {
         Constraint[] array = arguments.compile(scope); 
         Scope composite = extractor.extract(scope);
         FunctionDispatcher dispatcher = matcher.match(scope);
         Constraint result = dispatcher.compile(composite, NONE, array);
         
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            result = evaluation.compile(scope, result);
         }
         return result; 
      }
      
      private Constraint compile(Scope scope, String name, Constraint local) throws Exception {
         Constraint[] array = arguments.compile(scope); 
         Scope composite = extractor.extract(scope);
         FunctionDispatcher dispatcher = matcher.match(scope);
         Constraint result = dispatcher.compile(composite, local, array);
         
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            result = evaluation.compile(scope, result);
         }
         return result; 
      }

      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Address address = location.get();
         Value value = finder.findFunction(scope, address);
            
         if(value != null) { 
            Object object = value.getValue();
            Value constant = Constant.getConstant(value);
            
            if(Function.class.isInstance(object)) {
               return evaluate(scope, name, constant);
            }
         }
         return evaluate(scope, name);
      }

      private Value evaluate(Scope scope, String name) throws Exception {
         Object[] array = arguments.create(scope); 
         Invocation connection = cache.fetch(scope, array);
         Object object = connection.invoke(scope, NULL, array);
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
      
      private Value evaluate(Scope scope, String name, Value local) throws Exception {
         Object[] array = arguments.create(scope);
         Invocation connection = cache.fetch(scope, local, array);
         Object object = connection.invoke(scope, local, array);
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