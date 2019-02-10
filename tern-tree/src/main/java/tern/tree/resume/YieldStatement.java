package tern.tree.resume;

import static tern.core.result.Result.YIELD;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.Execution;
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

public class YieldStatement implements Compilation {
   
   private final Statement control;
   
   public YieldStatement(StringToken token){
      this(null, token);
   }
   
   public YieldStatement(Evaluation evaluation){
      this(evaluation, null);
   }
   
   public YieldStatement(Evaluation evaluation, StringToken token){
      this.control = new CompileResult(evaluation);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, control, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation evaluation;

      public CompileResult(Evaluation evaluation){
         this.evaluation = evaluation;
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
         }
         return new CompileExecution(evaluation);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final Evaluation evaluation;

      public CompileExecution(Evaluation evaluation){
         this.evaluation = evaluation;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         if(evaluation != null) {
            Value value = evaluation.evaluate(scope, null);
            Object object = value.getValue();

            return Result.getYield(object);
         }
         return YIELD;
      }
   }
}