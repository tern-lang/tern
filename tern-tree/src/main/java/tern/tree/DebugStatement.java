package tern.tree;

import static java.lang.Boolean.TRUE;
import static tern.core.result.Result.NORMAL;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.Execution;
import tern.core.IdentityEvaluation;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class DebugStatement implements Compilation {
   
   private final Evaluation condition;

   public DebugStatement(){
      this(null, null);
   }

   public DebugStatement(StringToken token){
      this(null, token);
   }
   
   public DebugStatement(Evaluation condition){
      this(condition, null);
   }

   public DebugStatement(Evaluation condition, StringToken token){
      this.condition = condition;
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      Statement statement = create(module, path, line);
      
      return new TraceStatement(interceptor, handler, statement, trace);
   }
   
   private Statement create(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getDebug(module, path, line);
      
      return new CompileResult(interceptor, condition, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final TraceInterceptor interceptor;
      private final Evaluation evaluation;
      private final Evaluation success;
      private final Trace trace;

      public CompileResult(TraceInterceptor interceptor, Evaluation evaluation, Trace trace){
         this.success = new IdentityEvaluation(TRUE);
         this.interceptor = interceptor;
         this.evaluation = evaluation;
         this.trace = trace;
      }

      @Override
      public boolean define(Scope scope) throws Exception {
         if(evaluation != null) {
            evaluation.define(scope);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         if(evaluation != null) {
            evaluation.compile(scope, null);
            return new CompileExecution(interceptor, evaluation, trace);
         }
         return new CompileExecution(interceptor, success, trace);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final TraceInterceptor interceptor;
      private final Evaluation evaluation;
      private final Trace trace;

      public CompileExecution(TraceInterceptor interceptor, Evaluation evaluation, Trace trace){
         this.interceptor = interceptor;
         this.evaluation = evaluation;
         this.trace = trace;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value value = evaluation.evaluate(scope, null);
         Object object = value.getValue();

         if(TRUE.equals(object)) {
            try {
               interceptor.traceBefore(scope, trace);
            } finally {
               interceptor.traceAfter(scope, trace);
            }
         }
         return NORMAL;
      }
   }
}