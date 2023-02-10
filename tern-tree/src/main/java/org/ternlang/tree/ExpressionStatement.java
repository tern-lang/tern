package org.ternlang.tree;

import static org.ternlang.core.constraint.Constraint.NONE;

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

public class ExpressionStatement implements Compilation {
   
   private final Statement expression;
   
   public ExpressionStatement(Evaluation expression) {
      this.expression = new CompileResult(expression);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, expression, trace);
   }
   
   private static class CompileResult extends Statement {
      
      private final Evaluation expression;
   
      public CompileResult(Evaluation expression) {
         this.expression = expression;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         expression.define(scope);
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         expression.compile(scope, NONE);
         return new CompileExecution(expression);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final Evaluation expression;
   
      public CompileExecution(Evaluation expression) {
         this.expression = expression;
      }      
   
      @Override
      public Result execute(Scope scope) throws Exception {
         Value reference = expression.evaluate(scope, null);
         Object value = reference.getValue();
         
         return Result.getNormal(value);
      }
   }
}