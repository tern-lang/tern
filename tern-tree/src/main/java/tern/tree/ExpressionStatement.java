package tern.tree;

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
         expression.compile(scope, null);
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