package tern.common;

import java.util.Set;

public abstract class ProgressCache<K, V, E extends Enum> implements Cache<K, V> {
   
   private final Cache<K, V> cache;
   private final E require;
   private final long delay;
   
   public ProgressCache(E require) {
      this(require, 10000);
   }
   
   public ProgressCache(E require, long delay) {
      this.cache = new CopyOnWriteCache<K, V>();
      this.require = require;
      this.delay = delay;
   }

   @Override
   public Set<K> keySet() {
      return cache.keySet();
   }

   @Override
   public V take(K key) {
      V value = cache.take(key);
      
      if(value != null) {
         Progress<E> progress = progress(value);
         progress.wait(require, delay);
      }
      return value;
   }

   @Override
   public V fetch(K key) {
      V value = cache.fetch(key);
      
      if(value != null) {
         Progress<E> progress = progress(value);
         progress.wait(require, delay);
      }
      return value;
   }

   @Override
   public boolean isEmpty() {
      return cache.isEmpty();
   }

   @Override
   public boolean contains(K key) {
      return cache.contains(key);
   }

   @Override
   public V cache(K key, V value) {
      return cache.cache(key, value);
   }

   @Override
   public void clear() {
      cache.clear();
   }

   @Override
   public int size() {
      return cache.size();
   }
   
   protected abstract Progress<E> progress(V value);

}
