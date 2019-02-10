package tern.tree.operation;

import junit.framework.TestCase;

import tern.core.variable.Value;
import tern.core.variable.ValueCache;

public class ValueCacheTest extends TestCase {
   
   private static final int ITERATIONS = 1000000;
   
   public void testValueCache() throws Exception {
      runIt("create: ", new Runnable() {
         @Override
         public void run(){
            for(int i = 0; i < ITERATIONS; i++) {
               for(int j = 0; j < 100; j++) {
                  Value.getTransient(j);
               }
            }
         }
      });
      
      runIt("cache: ", new Runnable() {
         @Override
         public void run(){
            for(int i = 0; i < ITERATIONS; i++) {
               for(int j = 0; j < 100; j++) {
                  ValueCache.getInteger(j);
               }
            }
         }
      });
   }
   
   public static void runIt(String message, Runnable task) {
      long start = System.currentTimeMillis();
      task.run();
      long finish = System.currentTimeMillis();
      System.err.println(message +": "+(finish-start));
   }

}
