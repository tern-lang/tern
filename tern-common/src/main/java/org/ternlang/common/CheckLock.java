package org.ternlang.common;

import java.util.concurrent.atomic.AtomicBoolean;

public class CheckLock {

   private final AtomicBoolean require;
   private final AtomicBoolean locked;
   
   public CheckLock() {
      this.require = new AtomicBoolean(true);
      this.locked = new AtomicBoolean();
   }
   
   public boolean require(Object value) {
      if(require.get()) {
         while(!locked.compareAndSet(false, true)) {
            synchronized(value) {
               try {
                  if(!require.get()) {
                     return false;
                  }
                  value.wait();
               }catch(Exception e) {
                  throw new ProgressException("Error waiting for lock", e);
               }
            }
         }
      }
      return require.get();
   }
   
   public boolean done(Object value) {
      if(locked.get()) {
         synchronized(value) {
            require.set(false);
            locked.set(false);
            value.notifyAll();
         }
      }
      return true;
   }
}
