package org.ternlang.common;

import java.util.concurrent.atomic.AtomicInteger;

public class LockProgress<T extends Enum> implements Progress<T> {
   
   private final AtomicInteger last;
   private final Object lock;
   
   public LockProgress() {
      this(-1);
   }
   
   public LockProgress(int value) {
      this.last = new AtomicInteger(value);
      this.lock = new Object();
   }
   
   @Override
   public boolean done(T phase){
      int ordinal = phase.ordinal();
      int latest = last.get();
      
      if(ordinal > latest) {
         synchronized(lock) {
            int current = last.get();
            
            if(ordinal > current) {
               last.set(ordinal);
               lock.notifyAll();
               return true;
            }
         }
      }
      return false;
   }
   
   @Override
   public boolean wait(T phase) {
      int ordinal = phase.ordinal();
      int latest = last.get();
      
      while(ordinal > latest) {
         synchronized(lock) {
            try {
               lock.wait();
            }catch(Exception e) {
               throw new ProgressException("Error waiting for " + phase, e);
            }
         }
         latest = last.get();
      }
      return ordinal <= latest;
   }

   @Override
   public boolean wait(T phase, long duration) {
      long time = System.currentTimeMillis();
      long expiry = Math.max(time, time + duration);
      int ordinal = phase.ordinal();
      int latest = last.get();
      
      while(ordinal > latest && duration > 0) {
         synchronized(lock) {
            try {
               lock.wait(duration);
            }catch(Exception e) {
               throw new ProgressException("Error waiting for " + phase, e);
            }
         }
         time = System.currentTimeMillis();
         duration = expiry - time;
         latest = last.get();
      }
      return ordinal <= latest;
   }
   
   @Override
   public boolean pass(T phase) {
      int ordinal = phase.ordinal();
      int latest = last.get();
      
      return latest >= ordinal;
   }
}