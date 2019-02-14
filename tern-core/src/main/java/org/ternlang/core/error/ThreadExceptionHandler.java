package org.ternlang.core.error;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadExceptionHandler  {

   private final ExceptionHandler handler;
   
   public ThreadExceptionHandler() {
      this.handler = new ExceptionHandler();
   }
   
   public void register() {
      Thread.setDefaultUncaughtExceptionHandler(handler);
   }
   
   private static class ExceptionHandler implements UncaughtExceptionHandler {
    
      @Override
      public void uncaughtException(Thread thread, Throwable cause) {
         cause.printStackTrace();
      }

   }
}