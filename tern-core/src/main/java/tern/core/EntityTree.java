package tern.core;

import tern.common.Cache;
import tern.common.CopyOnWriteCache;
import tern.core.type.Type;

public class EntityTree<K, V>  {

   private final EntityCache<Cache<K, V>> cache;
   
   public EntityTree() {
      this.cache = new EntityCache<Cache<K, V>>();
   }
   
   public Cache<K, V> get(Type type) {
      Cache<K, V> table = cache.fetch(type);
      
      if(table == null) {
         table = new CopyOnWriteCache<K, V>();
         cache.cache(type, table);
      }
      return table;
      
   }
}
