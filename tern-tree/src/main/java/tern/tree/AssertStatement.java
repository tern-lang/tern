package tern.tree;

import static java.lang.Boolean.TRUE;
import static tern.core.result.Result.NORMAL;

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

public class AssertStatement implements Compilation {
   
   private final Statement assertion;

   public AssertStatement(Evaluation evaluation){
      this.assertion = new CompileResult(evaluation);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, assertion, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation evaluation;

      public CompileResult(Evaluation evaluation){
         this.evaluation = evaluation;
      }

      @Override
      public boolean define(Scope scope) throws Exception {
         evaluation.define(scope);
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         evaluation.compile(scope, null);
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
         Value value = evaluation.evaluate(scope, null);
         Object object = value.getValue();

         if(!TRUE.equals(object)) {
            throw new AssertException("Assertion failed");
         }
         return NORMAL;
      }
   }
}