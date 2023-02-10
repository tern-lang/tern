package org.ternlang.core.trace;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class TraceEvaluation extends Evaluation {

   private final TraceInterceptor interceptor;
   private final Evaluation evaluation;
   private final Trace trace;
   
   public TraceEvaluation(TraceInterceptor interceptor, Evaluation evaluation, Trace trace) {
      this.interceptor = interceptor;
      this.evaluation = evaluation;
      this.trace = trace;
   }

   @Override
   public boolean expansion(Scope scope) throws Exception {
      return evaluation.expansion(scope);
   }

   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      try {
         return evaluation.compile(scope, left);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
      return NONE;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      try {
         interceptor.traceBefore(scope, trace);
         return evaluation.evaluate(scope, left); 
      } finally {
         interceptor.traceAfter(scope, trace);
      }
   }
}