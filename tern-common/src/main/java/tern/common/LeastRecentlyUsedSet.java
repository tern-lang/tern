package tern.common;

import java.util.AbstractSet;
import java.util.Iterator;

public class LeastRecentlyUsedSet<T> extends AbstractSet<T> {

   private final LeastRecentlyUsedMap<T, T> cache;
   
   public LeastRecentlyUsedSet() {
      this(1000);
   }
   
   public LeastRecentlyUsedSet(int capacity) {
      this.cache = new LeastRecentlyUsedMap<T, T>(capacity);
   }
 
   @Override
   public boolean contains(Object value) {
      return cache.containsKey(value);
   }
   
   @Override
   public boolean remove(Object value) {
      return cache.remove(value) != null;
   }
   
   @Override
   public boolean add(T value) {
      return cache.put(value,  value) != null;
   }
   
   @Override
   public Iterator<T> iterator() {
      return cache.keySet().iterator();
   }
   
   @Override
   public void clear() {
      cache.clear();
   }

   @Override
   public int size() {
      return cache.size();
   }
}