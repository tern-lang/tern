package org.ternlang.core.trace;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.stack.ThreadStack;

public class TraceInterceptor implements TraceListener {
   
   private final Set<TraceListener> listeners;
   private final TraceErrorCollector collector;
   private final ThreadStack stack;
   
   public TraceInterceptor(TraceErrorCollector collector, ThreadStack stack) {
      this.listeners = new CopyOnWriteArraySet<TraceListener>();
      this.collector = collector;
      this.stack = stack;
   }
   
   @Override
   public void traceBefore(Scope scope, Trace trace) {
      stack.before(trace);
      
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.traceBefore(scope, trace);
         }
      }
   }
   
   @Override
   public void traceAfter(Scope scope, Trace trace) {
      stack.after(trace);
      
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.traceAfter(scope, trace);
         }
      }
   }
   
   @Override
   public void traceCompileError(Scope scope, Trace trace, Exception cause) {
      collector.compileError(cause, trace);
      
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.traceCompileError(scope, trace, cause);
         }
      }
   }
   
   @Override
   public void traceRuntimeError(Scope scope, Trace trace, Exception cause) {
      collector.runtimeError(cause, trace);
      
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.traceRuntimeError(scope, trace, cause);
         }
      }
   }

   public void register(TraceListener listener) {
      listeners.add(listener);
   }
   
   public void remove(TraceListener listener) {
      listeners.remove(listener);
   }
   
   public void clear() {
      listeners.clear();
   }
}