package tern.core.trace;

import static tern.core.type.Category.OTHER;

import tern.core.error.ErrorHandler;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.type.Category;
import tern.core.type.Type;
import tern.core.type.TypeState;

public class TraceTypeState extends TypeState {

   private final TraceInterceptor interceptor;
   private final ErrorHandler handler;
   private final TypeState state;
   private final Trace trace;
   
   public TraceTypeState(TraceInterceptor interceptor, ErrorHandler handler, TypeState state, Trace trace) {
      this.interceptor = interceptor;
      this.handler = handler;
      this.state = state;
      this.trace = trace;
   }
   
   @Override
   public Category define(Scope scope, Type type) throws Exception {
      try {
         return state.define(scope, type);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
      return OTHER;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      try {
         state.compile(scope, type);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
   }
   
   @Override
   public void allocate(Scope scope, Type type) throws Exception {
      try {
         state.allocate(scope, type);
      }catch(Exception cause) {
         handler.failInternalError(scope, cause, trace);
      }
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception { 
      try {
         return state.execute(scope, type);
      }catch(Exception cause) {
         handler.failInternalError(scope, cause, trace);
      }
      return null;
   }

}
