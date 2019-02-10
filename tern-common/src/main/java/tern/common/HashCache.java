package tern.common;

import static java.util.Collections.EMPTY_MAP;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashCache<K, V> implements Cache<K, V> {
   
   private volatile Map<K, V> map;

   public HashCache() {
      this.map = EMPTY_MAP;
   }

   @Override
   public Set<K> keySet() {
      return map.keySet();
   }

   @Override
   public V take(K key) {
      return map.remove(key);
   }

   @Override
   public V fetch(K key) {      
      return map.get(key);
   }   

   @Override
   public boolean isEmpty() {      
      return map.isEmpty();
   }

   @Override
   public boolean contains(K key) {      
      return map.containsKey(key);
   }

   @Override
   public V cache(K key, V value) {
      if(map == EMPTY_MAP) {
         map = new HashMap<K, V>();
      }
      return map.put(key, value);
   }

   @Override
   public void clear() {
      map.clear();     
   }

   @Override
   public int size() {
      return map.size();
   }
   
   @Override
   public String toString() {
      return String.valueOf(map);
   }
}
