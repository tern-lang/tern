package tern.core.trace;

import static tern.core.constraint.Constraint.NONE;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;

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