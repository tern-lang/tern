package org.ternlang.core.trace;

import static org.ternlang.core.result.Result.NORMAL;

import org.ternlang.core.Execution;
import org.ternlang.core.NoExecution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

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