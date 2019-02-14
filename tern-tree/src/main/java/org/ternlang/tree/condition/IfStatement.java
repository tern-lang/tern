package org.ternlang.tree.condition;

import static org.ternlang.core.result.Result.NORMAL;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.core.variable.Value;

public class IfStatement implements Compilation {
   
   private final Statement branch;
   
   public IfStatement(Evaluation evaluation, Statement positive) {
      this(evaluation, positive, null);
   }
   
   public IfStatement(Evaluation evaluation, Statement positive, Statement negative) {
      this.branch = new CompileResult(evaluation, positive, negative);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, branch, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation condition;
      private final Statement positive;
      private final Statement negative;
      
      public CompileResult(Evaluation condition, Statement positive, Statement negative) {
         this.condition = condition;
         this.positive = positive;
         this.negative = negative;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         condition.define(scope);
         positive.define(scope);
         
         if(negative != null) {
            negative.define(scope);
         }       
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Constraint result = condition.compile(scope, null);
         Execution success = positive.compile(scope, returns);         
         Execution failure = null;
         
         if(negative != null){
            failure = negative.compile(scope, returns);
         }         
         return new CompileExecution(condition, success, failure);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final Evaluation condition;
      private final Execution positive;
      private final Execution negative;
      
      public CompileExecution(Evaluation condition, Execution positive, Execution negative) {
         this.condition = condition;
         this.positive = positive;
         this.negative = negative;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value result = condition.evaluate(scope, null);
         Object value = result.getValue();
         
         if(BooleanChecker.isTrue(value)) {
            return positive.execute(scope);
         } else {
            if(negative != null) {
               return negative.execute(scope);
            }
         }            
         return NORMAL;
      }
   }
}