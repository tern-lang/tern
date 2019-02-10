package tern.common;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import tern.common.LeastRecentlyUsedMap.RemovalListener;

public class LeastRecentlyUsedMapTest extends TestCase {

   public void testRemoval() {
      final Map<String, String> removedItems = new HashMap<String, String>();
      final RemovalListener<String, String> removalListener = new RemovalListener<String, String>() {

         @Override
         public void notifyRemoved(String key, String value) {
            removedItems.put(key, value);
         }
      };
      final LeastRecentlyUsedMap<String, String> leastRecentlyUsedMap = new LeastRecentlyUsedMap<String, String>(removalListener, 10);

      for (int i = 0; i < 20; i++) {
         leastRecentlyUsedMap.put("key-" + i, "value-" + i);
      }
      assertEquals(removedItems.size(), 10);
      assertEquals(leastRecentlyUsedMap.size(), 10);

      assertTrue(removedItems.containsKey("key-0"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-0"));
      assertTrue(removedItems.containsKey("key-1"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-1"));
      assertTrue(removedItems.containsKey("key-2"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-2"));
      assertTrue(removedItems.containsKey("key-3"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-3"));
      assertTrue(removedItems.containsKey("key-4"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-4"));
      assertTrue(removedItems.containsKey("key-5"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-5"));
      assertTrue(removedItems.containsKey("key-6"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-6"));
      assertTrue(removedItems.containsKey("key-7"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-7"));
      assertTrue(removedItems.containsKey("key-8"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-8"));
      assertTrue(removedItems.containsKey("key-9"));
      assertFalse(leastRecentlyUsedMap.containsKey("key-9"));

      assertTrue(leastRecentlyUsedMap.containsKey("key-10"));
      assertTrue(leastRecentlyUsedMap.containsKey("key-11"));
      assertTrue(leastRecentlyUsedMap.containsKey("key-12"));
      assertTrue(leastRecentlyUsedMap.containsKey("key-13"));
      assertTrue(leastRecentlyUsedMap.containsKey("key-14"));
      assertTrue(leastRecentlyUsedMap.containsKey("key-15"));
      assertTrue(leastRecentlyUsedMap.containsKey("key-16"));
      assertTrue(leastRecentlyUsedMap.containsKey("key-17"));
      assertTrue(leastRecentlyUsedMap.containsKey("key-18"));
      assertTrue(leastRecentlyUsedMap.containsKey("key-19"));

   }
}
