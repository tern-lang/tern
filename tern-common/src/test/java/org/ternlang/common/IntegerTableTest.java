package org.ternlang.common;

import java.lang.management.ManagementFactory;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import com.sun.management.ThreadMXBean;

public class IntegerTableTest extends TestCase {
   
   private static final int ITERATIONS = 1000;
   
   public void testComparison() throws Exception {
      ThreadMXBean bean = (ThreadMXBean)ManagementFactory.getThreadMXBean();
      Random random = new SecureRandom();
      int[] keys = new int[ITERATIONS];
      String[] values = new String[ITERATIONS];
      
      for(int i = 0; i < keys.length; i++) {
         keys[i] = Math.abs(random.nextInt());
         values[i] = "value-"+random.nextLong();
      }
      long id = Thread.currentThread().getId();
      for(int n = 0; n  < 10; n++) {
         IntegerTable<String> cache = new IntegerTable<String>(100);
         HashMap<Integer, String> map = new HashMap<Integer, String>(100);
         System.gc();
         Thread.sleep(1000);
         long memoryStart1 = bean.getThreadAllocatedBytes(id);
         long start1 = System.currentTimeMillis();
         
         for(int i = 0; i < values.length; i++) {
            cache.put(keys[i], values[i]);
         }
         long finish1 = System.currentTimeMillis();
         long memoryFinish1 = bean.getThreadAllocatedBytes(id);
         System.gc();
         Thread.sleep(1000);
         long memoryStart2 = bean.getThreadAllocatedBytes(id);
         long start2 = System.currentTimeMillis();
         
         for(int i = 0; i < values.length; i++) {
            map.put(keys[i], values[i]);
         }
         long finish2 = System.currentTimeMillis();
         long memoryFinish2 = bean.getThreadAllocatedBytes(id);
         System.err.println("PositionCache        time="+(finish1-start1) + " memory="+(memoryFinish1 - memoryStart1));
         System.err.println("LeastRecentlyUsedMap time="+(finish2-start2)+ " memory="+(memoryFinish2 - memoryStart2));
         
         Set<Integer> currentKeys = map.keySet(); // reconcile the keys
         
         for(Integer currentKey : currentKeys) {
            assertNotNull(map.get(currentKey));
            assertNotNull(cache.get(currentKey));
            assertEquals(cache.get(currentKey), map.get(currentKey));
         }
      }
   }

   public class IntegerTable<T> {

      private Object[] values;
      private int[] keys;
      private int size;
      
      public IntegerTable(int length) {
         this.values = new Object[0];
         this.keys = new int[0];
      }
      
      public T get(int key) {
         if(key < 0) {
            throw new IllegalArgumentException("Key must not be negative");
         }
         int begin = index(key, keys.length);
         
         for(int i = 0; i < keys.length; i++) {
            int position = (begin + i) % keys.length; 
            int current = keys[position];
         
            if(current < 0) {
               return null;
            }
            if(current == key) {
               return (T)values[position];
            }
         }
         return null;
      }
      
      public T put(int key, T value) {
         if(key < 0) {
            throw new IllegalArgumentException("Key must not be negative");
         }
         if(size * 5 >= keys.length) {
            int capacity = size * 5;
            int require = Math.max(capacity, 16);
            
            expand(require);
         }
         int begin = index(key, keys.length);
         
         for(int i = 0; i < keys.length; i++) {
            int position = (begin + i) % keys.length; // wrap around table
            int current = keys[position];
            
            if(current == key) {
               T previous = (T)values[position];
               
               values[position] = value;
               return previous;
            }
            if(current < 0) {
               keys[position] = key;
               values[position] = value; // slot was empty
               size++;
               return null;
            }
         }
         return null;
      }
      
      public boolean contains(int key) {
         if(key < 0) {
            throw new IllegalArgumentException("Key must not be negative");
         }
         int begin = index(key, keys.length);
         
         for(int i = 0; i < keys.length; i++) {
            int position = (begin + i) % keys.length;
            int current = keys[position];
         
            if(current < 0) {
               return false;
            }
            if(current == key) {
               return true;
            }
         }
         return false;
      }
      
      private void expand(int capacity) {
         int[] copy = new int[capacity];
         Object[] data = new Object[capacity];
         
         for(int i = 0; i < copy.length; i++) {
            copy[i] = -1;
         }
         for(int i = 0; i < keys.length; i++) {
            int key = keys[i];
            
            if(key >= 0) {
               int begin = index(key, copy.length);
               
               for(int j = 0; j < copy.length; j++) {
                  int position = (begin + j) % copy.length; // wrap around table
                  int current = copy[position];
                  
                  if(current < 0) {
                     Object value = values[i];
                     
                     copy[position] = key;
                     data[position] = value; // slot was empty
                     break;
                  }
               }
            }
         }
         keys = copy;
         values = data;
      }
      
      public boolean remove(int key) {
         if(key < 0) {
            throw new IllegalArgumentException("Key must not be negative");
         }
         int begin = index(key, keys.length);
         
         for(int i = 0; i < keys.length; i++) {
            int position = (begin + i) % keys.length; // next slot and wrap around
            int current = keys[position];
         
            if(current < 0) {
               return false;
            }
            if(current == key) {
               keys[position] = -1; 
               values[position] = null; // clear the slot
               size--;
               
               for(int j = 0; j < keys.length; j++) { // make sure we rehash
                  position = (begin + j) % keys.length; // slot after match
                  current = keys[position];

                  if(current < 0) {
                     return true; // no more slots to rehash
                  }
                  T value = (T)values[position];
                  
                  keys[position] = -1;
                  values[position] = null; // clear slot
                  size--; // decrease size
                  put(current, value); // add to fill up space
               }
               return true;
            }
         }
         return false;
      }
      
      private int index(int value, int length) {
         int hash = value;
        
         hash ^= hash >> 16; // murmur hash 3 integer finalizer 
         hash *= 0x85ebca6b;
         hash ^= hash >> 13;
         hash *= 0xc2b2ae35;
         hash ^= hash >> 16;
         
         return hash & (length-1);
      }
      
      public int size() {
         return size;
      }
   }

}

