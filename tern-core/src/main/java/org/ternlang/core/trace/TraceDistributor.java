package org.ternlang.core.trace;

import java.util.LinkedHashSet;
import java.util.Set;

import org.ternlang.core.scope.Scope;

public class TraceDistributor implements TraceListener {
   
   private volatile TraceListenerUpdater updater;
   private volatile TraceListener[] listeners;
   
   public TraceDistributor() {
      this.updater = new TraceListenerUpdater();
      this.listeners = new TraceListener[]{};
   }
   
   @Override
   public void traceBefore(Scope scope, Trace trace) {
      TraceListener[] local = listeners;
      
      for(int i = 0; i < local.length; i++) {
         local[i].traceBefore(scope, trace);
      }
   }
   
   @Override
   public void traceAfter(Scope scope, Trace trace) {
      TraceListener[] local = listeners;
      
      for(int i = 0; i < local.length; i++) {
         local[i].traceAfter(scope, trace);
      }
   }
   
   @Override
   public void traceCompileError(Scope scope, Trace trace, Exception cause) {
      TraceListener[] local = listeners;
      
      for(int i = 0; i < local.length; i++) {
         local[i].traceCompileError(scope, trace, cause);
      }
   }
   
   @Override
   public void traceRuntimeError(Scope scope, Trace trace, Exception cause) {
      TraceListener[] local = listeners;
      
      for(int i = 0; i < local.length; i++) {
         local[i].traceRuntimeError(scope, trace, cause);
      }
   }
   
   public void register(TraceListener listener) {
      updater.register(listener);
   }
   
   public void remove(TraceListener listener) {
      updater.remove(listener);
   }
   
   public void clear() {
      updater.clear();
   }
   
   private class TraceListenerUpdater {
      
      private final Set<TraceListener> state;
      private final TraceListener[] empty;
      
      public TraceListenerUpdater() {
         this.state = new LinkedHashSet<TraceListener>();
         this.empty = new TraceListener[]{};
      }

      public synchronized void register(TraceListener listener) {
         if(listener != null) {
            if(state.add(listener)) {
               listeners = state.toArray(empty);
            }
         }
      }
      
      public synchronized void remove(TraceListener listener) {
         if(listener != null) {
            if(state.remove(listener)) {
               listeners = state.toArray(empty);
            }
         }
      }
      
      public synchronized void clear() {
         state.clear();
         listeners = empty;
      }
   }
}