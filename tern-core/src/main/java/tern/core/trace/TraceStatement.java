package tern.core.trace;

import static tern.core.result.Result.NORMAL;

import tern.core.Execution;
import tern.core.NoExecution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.result.Result;
import tern.core.scope.Scope;

public class TraceStatement extends Statement {
   
   private final TraceInterceptor interceptor;
   private final ErrorHandler handler;
   private final Execution execution;
   private final Statement statement;
   private final Trace trace;
   
   public TraceStatement(TraceInterceptor interceptor, ErrorHandler handler, Statement statement, Trace trace) {
      this.execution = new NoExecution(NORMAL);
      this.interceptor = interceptor;
      this.statement = statement;
      this.handler = handler;
      this.trace = trace;
   }
   
   @Override
   public void create(Scope scope) throws Exception {
      try {
         statement.create(scope);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }    
   }
   
   @Override
   public boolean define(Scope scope) throws Exception {
      try {
         return statement.define(scope);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
      return true;
   }
   
   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      try {
         Execution execution = statement.compile(scope, returns);
         return new TraceExecution(interceptor, handler, execution, trace);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
      return execution;
   }
   
   private static class TraceExecution extends Execution {
      
      private final TraceInterceptor interceptor;
      private final ErrorHandler handler;
      private final Execution execution;
      private final Trace trace;
      
      public TraceExecution(TraceInterceptor interceptor, ErrorHandler handler, Execution execution, Trace trace) {
         this.interceptor = interceptor;
         this.execution = execution;
         this.handler = handler;
         this.trace = trace;
      }
   
      @Override
      public Result execute(Scope scope) throws Exception {
         try {
            interceptor.traceBefore(scope, trace);
            return execution.execute(scope); 
         } catch(Exception cause) {
            interceptor.traceRuntimeError(scope, trace, cause);
            return handler.failInternalError(scope, cause, trace);
         } finally {
            interceptor.traceAfter(scope, trace);
         }
      }
   }
}