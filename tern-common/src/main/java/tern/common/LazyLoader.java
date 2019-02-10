package tern.common;

public class LazyLoader<K, V> {
   
   private LazyBuilder<K, V> builder;
   private V value;
   
   public LazyLoader(LazyBuilder<K, V> builder) {
      this.builder = builder;
   }
   
   public V load(K key) {
      if(value == null) {
         value = builder.create(key);
      }
      return value;
   }
}
