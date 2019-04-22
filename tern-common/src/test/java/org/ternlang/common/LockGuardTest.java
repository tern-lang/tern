package org.ternlang.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

public class LockGuardTest extends TestCase {

   public void testGuard() throws Exception {
      LockGuard<Object> guard = new LockGuard<Object>(true);
      Object lock = new Object();
      
      assertTrue(guard.require(lock));
      assertTrue(guard.done(lock));
      assertFalse(guard.require(lock));
   }
   
   public void testGuardInTheadPool() throws Exception {
      LockGuard<Object> guard = new LockGuard<Object>(true);
      AtomicInteger counter = new AtomicInteger();
      CountDownLatch execute = new CountDownLatch(10);
      CountDownLatch done = new CountDownLatch(10);
      Incrementer incrementer = new Incrementer(execute, done, counter, guard);
      ExecutorService service = new ScheduledThreadPoolExecutor(10);
      
      for(int i = 0; i < 10; i++) {
         service.execute(incrementer);
      }
      done.await();
      
      assertFalse(guard.require(incrementer));
      assertTrue(guard.done(incrementer));
      assertEquals(counter.get(), 1);
      service.shutdown();
   }

   private static class Incrementer implements Runnable {
      
      private final CountDownLatch execute;
      private final CountDownLatch done;
      private final AtomicInteger counter;
      private final Guard<Object> guard;
      
      public Incrementer(CountDownLatch execute, CountDownLatch done, AtomicInteger counter, Guard<Object> guard) {
         this.counter = counter;
         this.execute = execute;
         this.guard = guard;
         this.done = done;
      }
      
      @Override
      public void run() {
         try {
            execute.countDown();
            execute.await();
            
            if(guard.require(this)) {
               counter.getAndIncrement();
               guard.done(this);
            }
         }catch(Exception e) {
            e.printStackTrace();
         } finally {
            done.countDown();
         }
      }
   }
}
