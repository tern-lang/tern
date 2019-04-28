package org.ternlang.core.trace;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeStack;

public class TraceInterceptor implements TraceListener {
   
   private final TraceErrorCollector collector;
   private final TraceDistributor distributor;
   
   public TraceInterceptor(TraceErrorCollector collector) {
      this.distributor = new TraceDistributor();
      this.collector = collector;
   }
   
   @Override
   public void traceBefore(Scope scope, Trace trace) {
      ScopeStack stack = scope.getStack();
      
      stack.before(trace);
      distributor.traceBefore(scope, trace);
   }
   
   @Override
   public void traceAfter(Scope scope, Trace trace) {
      ScopeStack stack = scope.getStack();
      
      stack.after(trace);
      distributor.traceAfter(scope, trace);
   }
   
   @Override
   public void traceCompileError(Scope scope, Trace trace, Exception cause) {
      collector.compileError(cause, trace);
      distributor.traceCompileError(scope, trace, cause);
   }
   
   @Override
   public void traceRuntimeError(Scope scope, Trace trace, Exception cause) {
      collector.runtimeError(cause, trace);
      distributor.traceRuntimeError(scope, trace, cause);
   }

   public void register(TraceListener listener) {
      distributor.register(listener);
   }
   
   public void remove(TraceListener listener) {
      distributor.remove(listener);
   }
   
   public void clear() {
      distributor.clear();
   }
}