package tern.core.stack;

import tern.core.function.Function;
import tern.core.trace.Trace;

public class ThreadStack {
   
   private final StackTraceBuilder builder;
   private final ThreadLocalStack local;
   
   public ThreadStack() {
      this.builder = new StackTraceBuilder();
      this.local = new ThreadLocalStack();
   }
   
   public StackTraceElement[] build() {
      return build(null);
   }
   
   public StackTraceElement[] build(Throwable cause) {
      StackTrace stack = local.get();
      
      if(cause != null) {
         return builder.create(stack, cause);   
      }
      return builder.create(stack);
   }
   
   public void before(Trace trace) {
      StackTrace stack = local.get();
      
      if(trace != null) {
         stack.before(trace);
      }
   }
   
   public void before(Function function) {
      StackTrace stack = local.get();
      
      if(function != null) {
         stack.before(function);
      }
   }
   
   public void after(Trace trace) { // remove from stack
      StackTrace stack = local.get();
      
      if(trace != null) {
         stack.after(trace);
      }
   }
   
   public void after(Function function) {
      StackTrace stack = local.get();
      
      if(function != null) {
         stack.after(function);
      }
   }
  
   public Function current() {
      StackTrace stack = local.get();
      return stack.current();
   }
   
   public void clear() {
      StackTrace stack = local.get();
      stack.clear();
   }
}