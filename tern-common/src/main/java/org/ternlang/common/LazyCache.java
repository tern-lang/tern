package org.ternlang.common;

import java.util.Set;

public class LazyCache<K, V> implements Cache<K, V> {

   private final LazyBuilder<K, V> builder;
   private final Cache<K, V> cache;

   public LazyCache(LazyBuilder<K, V> builder) {
      this(builder, 16);
   }

   public LazyCache(LazyBuilder<K, V> builder, int size) {
      this.cache = new CopyOnWriteCache<K, V>(size);
      this.builder = builder;
   }
   
   @Override
   public void clear() {
      cache.clear();
   }

   @Override
   public int size() {
      return cache.size();
   }

   @Override
   public Set<K> keySet() {
      return cache.keySet();
   }

   @Override
   public V fetch(K key) {
      V value = cache.fetch(key);
      
      if(value == null) {
         value = builder.create(key);
         cache.cache(key, value);
      }
      return value;
   }

   @Override
   public V cache(K key, V value) {
      return cache.cache(key, value);
   }

   @Override
   public V take(K key) {
      return cache.take(key);
   }

   @Override
   public boolean contains(K key) {
      return cache.contains(key);
   }

   @Override
   public boolean isEmpty() {
      return cache.isEmpty();
   }
   
   @Override
   public String toString() {
      return String.valueOf(cache);
   }
}