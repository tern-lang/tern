package tern.parse;

public class ArrayPositionCache<T> {
   
   private final ArrayTable<T> table;
   private final ArrayQueue queue;
   private final Object[] cache;
   private final int capacity;
   
   public ArrayPositionCache(int capacity) {
      this.cache = new Object[capacity * 20];
      this.queue = new ArrayQueue(cache, capacity * 19, capacity);
      this.table = new ArrayTable<T>(cache, 0, capacity * 19);
      this.capacity = capacity;
   }
   
   public synchronized T fetch(Long key) {
      return table.get(key);
   }
   
   public synchronized void cache(Long key, T value) {
      T previous = table.put(key, value);
      
      if(previous == null) {
         int size = queue.size();
         
         if(size >= capacity) {
            Object last = queue.poll();
            
            if(last != null) {
               table.remove(last);
            }
         }
         queue.offer(key);
      }
   }
   
   public synchronized boolean contains(Long key) {
      return table.contains(key);
   }
   
   public synchronized int size() {
      return table.size();
   }
}
