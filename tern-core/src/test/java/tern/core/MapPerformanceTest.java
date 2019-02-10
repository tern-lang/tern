package tern.core;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

public class MapPerformanceTest extends TestCase {
   
   public void testPerformance() throws Exception {
      Object[] largeSource = new Object[50000];
      Object[] smallSource = new Object[30];
      Map<Object, Object> largeConcurrentMap = new ConcurrentHashMap<Object, Object>();
      Map<Object, Object> largeHashMap = new HashMap<Object, Object>();
      Map<Object, Object> smallConcurrentMap = new ConcurrentHashMap<Object, Object>();
      Map<Object, Object> smallHashMap = new HashMap<Object, Object>();
      Random random = new SecureRandom();
      
      for(int i = 0; i < largeSource.length; i+=2) {
         largeSource[i] = random.nextLong();
         largeSource[i+1] = "value-"+i;
      }
      checkPerformance(largeSource, largeConcurrentMap, "ConcurrentHashMap", 20);
      checkPerformance(largeSource, largeHashMap,       "          HashMap", 20);
      
      for(int i = 0; i < largeSource.length; i+=2) {
         largeSource[i] = "key-"+random.nextLong();
         largeSource[i+1] = "value-"+i;
      }
      checkPerformance(largeSource, largeConcurrentMap, "ConcurrentHashMap", 20);
      checkPerformance(largeSource, largeHashMap,       "          HashMap", 20);
      checkPerformance(largeSource, largeConcurrentMap, "ConcurrentHashMap", 20);
      checkPerformance(largeSource, largeHashMap,       "          HashMap", 20);
      
      System.err.println();
      
      for(int i = 0; i < smallSource.length; i+=2) {
         smallSource[i] = random.nextLong();
         smallSource[i+1] = "value-"+i;
      }
      checkPerformance(smallSource, smallConcurrentMap, "ConcurrentHashMap", 20000);
      checkPerformance(smallSource, smallHashMap,       "          HashMap", 20000);
      
      for(int i = 0; i < smallSource.length; i+=2) {
         smallSource[i] = "key-"+random.nextLong();
         smallSource[i+1] = "value-"+i;
      }
      checkPerformance(smallSource, smallConcurrentMap, "ConcurrentHashMap", 20000);
      checkPerformance(smallSource, smallHashMap,       "          HashMap", 20000);
      checkPerformance(smallSource, smallConcurrentMap, "ConcurrentHashMap", 20000);
      checkPerformance(smallSource, smallHashMap,       "          HashMap", 20000);
   }
   
   private void checkPerformance(Object[] source, Map<Object, Object> map, String prefix, int iterations) throws Exception {
      System.gc();
      System.gc();
      Thread.sleep(100);
      long start = System.nanoTime();
      for(int n = 0; n < iterations; n++) {

         for(int i = 0; i < source.length; i+=2) {
            map.put(source[i], source[i+1]);
         }
         for(int i = 0; i < source.length; i+=2) {
            map.get(source[i]);
         }
         map.clear();
      }
      long end = System.nanoTime();
      long duration = TimeUnit.NANOSECONDS.toMillis(end-start);
      
      System.err.println(prefix+ ": size="+source.length + " iterations="+iterations+ " duration="+duration);
   }
}
