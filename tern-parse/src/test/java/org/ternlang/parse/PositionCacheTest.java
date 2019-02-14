package org.ternlang.parse;

import java.lang.management.ManagementFactory;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.ternlang.common.LeastRecentlyUsedMap;

import com.sun.management.ThreadMXBean;

public class PositionCacheTest extends TestCase {
   
   private static final int ITERATIONS = 100;
   
   public void testComparison() throws Exception {
      ThreadMXBean bean = (ThreadMXBean)ManagementFactory.getThreadMXBean();
      Random random = new SecureRandom();
      Long[] keys = new Long[ITERATIONS];
      String[] values = new String[ITERATIONS];
      
      for(int i = 0; i < keys.length; i++) {
         keys[i] = random.nextLong();
         values[i] = "value-"+random.nextLong();
      }
      long id = Thread.currentThread().getId();
      for(int n = 0; n  < 10; n++) {
         ArrayPositionCache<String> cache = new ArrayPositionCache<String>(100);
         LeastRecentlyUsedMap<Long, String> map = new LeastRecentlyUsedMap<Long, String>(100);
         System.gc();
         Thread.sleep(100);
         long memoryStart1 = bean.getThreadAllocatedBytes(id);
         long start1 = System.currentTimeMillis();
         
         for(int i = 0; i < values.length; i++) {
            cache.cache(keys[i], values[i]);
         }
         long finish1 = System.currentTimeMillis();
         long memoryFinish1 = bean.getThreadAllocatedBytes(id);
         System.gc();
         Thread.sleep(100);
         long memoryStart2 = bean.getThreadAllocatedBytes(id);
         long start2 = System.currentTimeMillis();
         
         for(int i = 0; i < values.length; i++) {
            map.put(keys[i], values[i]);
         }
         long finish2 = System.currentTimeMillis();
         long memoryFinish2 = bean.getThreadAllocatedBytes(id);
         System.err.println("PositionCache        time="+(finish1-start1) + " memory="+(memoryFinish1 - memoryStart1));
         System.err.println("LeastRecentlyUsedMap time="+(finish2-start2)+ " memory="+(memoryFinish2 - memoryStart2));
         
         Set<Long> currentKeys = map.keySet(); // reconcile the keys
         
         for(Long currentKey : currentKeys) {
            assertNotNull(map.get(currentKey));
            assertNotNull(cache.fetch(currentKey));
            assertEquals(cache.fetch(currentKey), map.get(currentKey));
         }
      }
   }

}
