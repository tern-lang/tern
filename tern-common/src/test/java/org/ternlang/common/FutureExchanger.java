package org.ternlang.common;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class FutureExchanger<K, V> implements Exchanger<K, V>{

   private final ExchangeBuilder builder;
   
   public FutureExchanger() {
      this(60000);
   }
   
   public FutureExchanger(long wait){
      this.builder = new ExchangeBuilder(wait);
   }
   
   @Override
   public V get(K key){
      Exchange exchange = builder.create(key);
      return exchange.get();
   }
   
   @Override
   public void set(K key, V value) {
      Exchange exchange = builder.create(key);
      exchange.set(value);
   }
   
   public class ExchangeBuilder {
   
      private final ConcurrentMap<K, Exchange> cache;
      private final long wait;
      
      public ExchangeBuilder(long wait){
         this.cache = new ConcurrentHashMap<K, Exchange>();
         this.wait = wait;
      }
      
      public Exchange create(K key) {
         Exchange exchange = new Exchange(wait);
         
         if(cache.putIfAbsent(key, exchange) != null) {
            return cache.get(key);
         }
         return exchange;
      }
   }
   
   public class Exchange {
      
      private final AtomicReference<V> reference;
      private final CountDownLatch latch;
      private final long wait;
      
      public Exchange(long wait) {
         this.reference = new AtomicReference<V>();
         this.latch = new CountDownLatch(1);
         this.wait = wait;
      }
      
      public void set(V value) {
         latch.countDown();
         reference.set(value);
      }

      public V get() {
         try {
            latch.await(wait, MILLISECONDS);
         }catch(Exception e) {
            throw new IllegalStateException("Exchange duration has expired", e);
         }
         return reference.get();
      } 
   }
   
   
}
