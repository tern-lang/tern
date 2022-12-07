package org.ternlang.core.stack;

public class ThreadStack {

   private final StackTraceElement[] elements; // needed to initialize caches
   private final StackTraceBuilder builder;
   private final ThreadLocalStack local;
   
   public ThreadStack() {
      this.elements = new StackOverflowError().getStackTrace();
      this.builder = new StackTraceBuilder();
      this.local = new ThreadLocalStack();
   }
   
   public StackTrace trace() {
      return local.get();
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
}