package org.ternlang.common;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class LockGuard<T> implements Guard<T> {

   private final AtomicReference<T> lock;
   private final AtomicBoolean require;
   private final AtomicBoolean block;
   
   public LockGuard() {
      this(true);
   }
   
   public LockGuard(boolean block) {
      this.lock = new AtomicReference<T>();
      this.require = new AtomicBoolean(true);
      this.block = new AtomicBoolean(block);
   }
   
   @Override
   public boolean require(T value) { // like a locking boolean
      if(require.get()) {
         while(!lock.compareAndSet(null, value)) { // must be same lock used
            if(!block.get()) { 
               return false; // no block needed as other thread progressed
            }
            synchronized(value) { 
               try {
                  if(!require.get()) {
                     return false; // already done
                  }
                  value.wait();
               }catch(Exception e) {
                  throw new ProgressException("Error waiting for lock", e);
               }
            }
         }
      }
      return require.get(); // lock reference may be held
   }
   
   @Override
   public boolean done(T value) {
      if(lock.compareAndSet(value, value)) { 
         if(block.get()) { 
            synchronized(value) { 
               require.set(false);
               lock.set(null);
               value.notifyAll(); // notify all blocked
            }
         } 
         require.set(false);
         lock.set(null);
      }
      return !require.get();
   }
}
