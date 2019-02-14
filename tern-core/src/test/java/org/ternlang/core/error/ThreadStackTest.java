package org.ternlang.core.error;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.ternlang.core.stack.ThreadStack;

import junit.framework.TestCase;

public class ThreadStackTest extends TestCase {

   private static final int ITERATIONS = 100000000;
   private static final int ELEMENTS = 10;
   
   public void testThreadStack() throws Exception {
      final ThreadStack stack = new ThreadStack();
      final Map<String, Object> map = new HashMap<String, Object>();
      final Random random = new SecureRandom();
      final String[] names = new String[ELEMENTS];
      
      for(int i = 0; i < ELEMENTS; i++) {
         int value = random.nextInt(ELEMENTS);
         String key = String.valueOf(value);
         names[i] = key;
         map.put(key, value);
      }
      
      timeIt(new Runnable() {
         @Override
         public void run(){
            for(int i = 0; i < ITERATIONS; i++){
               String key = names[i%ELEMENTS];
               map.get(key);
            }
         }
      }, "Map.get");
      
      timeIt(new Runnable() {
         @Override
         public void run(){
            for(int i = 0; i < ITERATIONS; i++){
               stack.current();
            }
         }
      }, "ThreadStack.current");
      
//      timeIt(new Runnable() {
//         @Override
//         public void run(){
//            for(int i = 0; i < ITERATIONS; i++){
//               String key = names[i%ELEMENTS];
//               map.get(key);
//            }
//         }
//      }, "Map.get");
//      
//      timeIt(new Runnable() {
//         @Override
//         public void run(){
//            for(int i = 0; i < ITERATIONS; i++){
//               stack.get();
//            }
//         }
//      }, "ThreadStack.get");
   }
   
   public static void timeIt(Runnable run, String message) {
      long start = System.currentTimeMillis();
      run.run();
      long finish = System.currentTimeMillis();
      System.err.println(message+": "+(finish-start));
   }
}
