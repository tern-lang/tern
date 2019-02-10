package tern.core;

import tern.common.Cache;
import tern.common.CopyOnWriteCache;
import tern.common.CopyOnWriteSparseArray;
import tern.common.SparseArray;

public class EntityCache<V> {

   private final SparseArray<V> array;
   private final Cache<Entity, V> cache;
   
   public EntityCache() {
      this(10000);
   }
   
   public EntityCache(int capacity) {
      this.array = new CopyOnWriteSparseArray<V>(capacity); // for order > 0
      this.cache = new CopyOnWriteCache<Entity, V>(); // for order = 0
   }

   public V take(Entity type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.take(type);
      }
      return array.remove(order);
   }

   public V fetch(Entity type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.fetch(type);
      }
      return array.get(order);
   }

   public boolean contains(Entity type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.contains(type);
      }
      return array.get(order) != null;
   }

   public void cache(Entity type, V value) {
      int order = type.getOrder();
      
      if(order == 0) {
         cache.cache(type, value);
      }
      array.set(order, value);
   }
}