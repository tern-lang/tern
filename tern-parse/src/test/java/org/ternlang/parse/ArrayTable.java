package org.ternlang.parse;

public class ArrayTable<T> {

   private Object[] table;
   private int length;
   private int start;
   private int size;
   
   public ArrayTable(Object[] table, int start, int length) {
      this.length = length / 2;
      this.start = start;
      this.table = table;
   }
   
   public T get(Object key) {
      if(key == null) {
         throw new IllegalArgumentException("Key must not be null");
      }
      int begin = index(key);
      
      for(int i = 0; i < length; i++) {
         int position = (begin + i) % length; 
         int index = (position * 2) + start;
         Object current = table[index];
      
         if(current == null) {
            return null;
         }
         if(current == key || key.equals(current)) {
            return (T)table[index + 1];
         }
      }
      return null;
   }
   
   public T put(Object key, T value) {
      if(key == null) {
         throw new IllegalArgumentException("Key must not be null");
      }
      int begin = index(key);
      
      for(int i = 0; i < length; i++) {
         int position = (begin + i) % length; // wrap around table
         int index = (position * 2) + start;
         Object current = table[index];
         
         if(current == key || key.equals(current)) {
            T previous = (T)table[index + 1];
            
            table[index+1] = value;
            return previous;
         }
         if(current == null) {
            table[index] = key;
            table[index + 1] = value; // slot was empty
            size++;
            return null;
         }
      }
      return null;
   }
   
   public boolean contains(Object key) {
      if(key == null) {
         throw new IllegalArgumentException("Key must not be null");
      }
      int begin = index(key);
      
      for(int i = 0; i < length; i++) {
         int position = (begin + i) % length; 
         int index = (position * 2) + start;
         Object current = table[index];
      
         if(current == null) {
            return false;
         }
         if(current == key || key.equals(current)) {
            return true;
         }
      }
      return false;
   }
   
   public boolean remove(Object key) {
      if(key == null) {
         throw new IllegalArgumentException("Key must not be null");
      }
      int begin = index(key);
      
      for(int i = 0; i < length; i++) {
         int position = (begin + i) % length; // next slot and wrap around
         int index = (position * 2) + start;
         Object current = table[index];
      
         if(current == null) {
            return false;
         }
         if(current == key || key.equals(current)) {
            table[index] = null; // enable gc
            table[index + 1] = null; // clear the slot
            begin = position + 1;
            size--;
            
            for(int j = 0; j < length; j++) { // make sure we rehash
               position = (begin + j) % length; // slot after match
               index = (position * 2) + start;
               current = table[index];

               if(current == null) {
                  return true; // no more slots to rehash
               }
               T value = (T)table[index + 1];
               
               table[index] = null; // enable gc
               table[index + 1] = null; // clear slot
               size--; // decrease size
               put(current, value); // add to fill up space
            }
            return true;
         }
      }
      return false;
   }
   
   private int index(Object value) {
      int hash = value.hashCode();
      
      hash ^= hash >> 16; // murmur hash 3 integer finalizer 
      hash *= 0x85ebca6b;
      hash ^= hash >> 13;
      hash *= 0xc2b2ae35;
      hash ^= hash >> 16;
      
      return hash & (length-1);
   }
   
   public int size() {
      return size;
   }
}
