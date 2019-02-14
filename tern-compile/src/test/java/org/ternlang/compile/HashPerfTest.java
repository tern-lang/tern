package org.ternlang.compile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

public class HashPerfTest extends TestCase {
   
   public static final class Identifier {
      public static final AtomicInteger i = new AtomicInteger();
      public final Integer hash;
      
      public Identifier() {
         this.hash = i.getAndIncrement();
      }
      
      @Override
      public final int hashCode() {
         return hash;
      }
      
      @Override
      public final boolean equals(Object object) {
         return object == this;
      }
   }
   public static final class Key{
      private final Identifier identity;
      private final String key;
      private final Integer integer;
      public Key(String key, Integer integer){
         this.identity = new Identifier();
         this.integer = integer;
         this.key = key;
      }
   }
   
   public void testHashPerf() throws Exception{
      Map<Object, String> map = new ConcurrentHashMap<Object, String>();
      Key[] keys = new Key[1000000];
      for(int i = 0; i < keys.length; i++) {
         keys[i]=new Key("key-"+i, i);
      }
      for(int n = 0; n < 2; n++) {
         long time1 = System.currentTimeMillis();
         for(int i = 0; i < keys.length; i++) {
            map.put(keys[i].key, keys[i].key);
         }
         map.clear();
         long time2 = System.currentTimeMillis();
         for(int i = 0; i < keys.length; i++) {
            map.put(keys[i], keys[i].key);
         }
         map.clear();
         long time3 = System.currentTimeMillis();
         for(int i = 0; i < keys.length; i++) {
            map.put(keys[i].integer, keys[i].key);
         }
         map.clear();
         long time4 = System.currentTimeMillis();
         for(int i = 0; i < keys.length; i++) {
            map.put(keys[i].identity, keys[i].key);
         }
         map.clear();
         long time5 = System.currentTimeMillis();
         for(int i = 0; i < keys.length; i++) {
            map.put(keys[i].identity.hash, keys[i].key);
         }
         map.clear();
         long time6 = System.currentTimeMillis();
         for(int i = 0; i < keys.length; i++) {
            map.put(keys[i].integer, keys[i].key);
         }
         map.clear();
         long time7 = System.currentTimeMillis();
         System.err.println("string="+(time2-time1)+" key="+(time3-time2)+ " integer="+(time4-time3)+" identity="+(time5-time4)+" hash="+(time6-time5)+" integer="+(time7-time6));
      }
   }

}
