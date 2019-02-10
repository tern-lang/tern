package tern.tree;

import static tern.core.Reserved.TYPE_NULL;

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

public class ThrowStatement implements Compilation {
   
   private final Statement control;
   
   public ThrowStatement(Evaluation evaluation) {
      this.control = new CompileResult(evaluation);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceStatement(interceptor, handler, control, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation evaluation;
      
      public CompileResult(Evaluation evaluation) {
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
      
      public CompileExecution(Evaluation evaluation) {
         this.evaluation = evaluation; 
      }   
   
      @Override
      public Result execute(Scope scope) throws Exception {
         Value reference = evaluation.evaluate(scope, null);
         Module module = scope.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         Object value = reference.getValue();

         if(value == null) {
            return handler.failInternalError(scope, TYPE_NULL); // don't throw null
         }
         return handler.failInternalError(scope, value);
      }
   }

}