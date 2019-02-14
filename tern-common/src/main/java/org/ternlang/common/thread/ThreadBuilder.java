package org.ternlang.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadBuilder implements ThreadFactory {   
   
   private static final String THREAD_TEMPLATE = "%s: Thread-%s";
   private static final String THREAD_DEFAULT = "Thread";
   
   private final ThreadNameBuilder builder;
   private final boolean daemon;   
   private final int stack;
   
   public ThreadBuilder() {
      this(true);
   }   

   public ThreadBuilder(boolean daemon) {
      this(daemon, 0);
   }
   
   public ThreadBuilder(boolean daemon, int stack) {
      this.builder = new ThreadNameBuilder();
      this.daemon = daemon;
      this.stack = stack;
   }
   
   @Override
   public Thread newThread(Runnable task) {
      Thread thread = new Thread(null, task, THREAD_DEFAULT, stack);
      
      if(task != null) {
         String name = builder.createName(task);
         
         thread.setDaemon(daemon);
         thread.setName(name);
      }
      return thread;
   }

   public Thread newThread(Runnable task, Class type) {
      Thread thread = new Thread(null, task, THREAD_DEFAULT, stack);
      
      if(task != null) {
         String name = builder.createName(type);
         
         thread.setDaemon(daemon);
         thread.setName(name);
      }
      return thread;
   }
   
   private class ThreadNameBuilder {
      
      private final AtomicInteger counter;
      
      public ThreadNameBuilder() {
         this.counter = new AtomicInteger(1);
      }
   
      private String createName(Runnable task) {
         Class type = task.getClass();
         String prefix = type.getSimpleName();      
         int count = counter.getAndIncrement();
   
         return String.format(THREAD_TEMPLATE, prefix, count);
      }
      
      private String createName(Class type) {
         String prefix = type.getSimpleName();
         int count = counter.getAndIncrement();
   
         return String.format(THREAD_TEMPLATE, prefix, count);
      }
   }
}