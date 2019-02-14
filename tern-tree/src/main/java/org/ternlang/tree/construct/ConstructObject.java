package org.ternlang.tree.construct;

import static org.ternlang.core.ModifierType.ABSTRACT;
import static org.ternlang.core.ModifierType.ENUM;
import static org.ternlang.core.ModifierType.MODULE;
import static org.ternlang.core.ModifierType.TRAIT;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceEvaluation;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.tree.ArgumentList;

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