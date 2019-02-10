package tern.core;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

public class HashMapLoadFactorTest extends TestCase {
   
   private static final int ITERATIONS = 10;
   private static final int CAPACITY = 1000000;
   
   public void testLoadFactor() throws Exception{
      Random random = new SecureRandom();
      String[] keys = new String[CAPACITY];
      
      for(int i = 0; i< keys.length; i++){
         keys[i] = "key-" + random.nextInt(CAPACITY);
      }
      for(int i = 0; i < ITERATIONS; i++) {
         measurePerformance(keys, 0.75f);    
         measurePerformance(keys, 0.50f);  
         measurePerformance(keys, 0.35f); 
      }
   }
   
   private void measurePerformance(String[] keys, float loadFactor) {
      Map<String, String> map = new HashMap<String, String>(keys.length, loadFactor);
      for(int i = 0; i< keys.length; i++){
         map.put(keys[i], keys[i]);
      }
      long start = System.currentTimeMillis();
      for(int i = 0; i< keys.length; i++){
         map.get(i);
      }
      long finish = System.currentTimeMillis();
      System.err.println("loadFactor="+loadFactor+" time="+(finish-start));
   }

}
