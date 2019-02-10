package tern.tree.construct;

import static tern.core.ModifierType.ABSTRACT;
import static tern.core.ModifierType.ENUM;
import static tern.core.ModifierType.MODULE;
import static tern.core.ModifierType.TRAIT;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.function.resolve.FunctionResolver;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.trace.Trace;
import tern.core.trace.TraceEvaluation;
import tern.core.trace.TraceInterceptor;
import tern.tree.ArgumentList;

public class ConstructObject implements Compilation {
   
   private final ArgumentList arguments;
   private final Constraint type;
   
   public ConstructObject(Constraint type) {
      this(type, null);         
   }
   
   public ConstructObject(Constraint type, ArgumentList arguments) {
      this.arguments = arguments;
      this.type = type;
   } 
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Evaluation construct = create(module, path, line);
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private Evaluation create(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      FunctionResolver resolver = context.getResolver();
      
      return new CreateObject(resolver, handler, type, arguments, ABSTRACT.mask | TRAIT.mask | ENUM.mask | MODULE.mask);
   }
}