package org.ternlang.parse;

public class ArraySet {

   private Object[] table;
   private int length;
   private int start;
   private int size;
   
   public ArraySet(Object[] table, int start, int length) {
      this.length = length;
      this.start = start;
      this.table = table;
   }
   
   public boolean add(Object value) {
      if(value == null) {
         throw new IllegalArgumentException("Value must not be null");
      }
      int begin = index(value);
      
      for(int i = 0; i < length; i++) {
         int index = (begin + i) % length; // wrap around table
         Object current = table[start + index];
      
         if(current == null) {
            table[start + index] = value; // slot was empty
            size++;
            return true;
         }
         if(current == value || value.equals(current)) {
            return false; // already there
         }
      }
      return false;
   }
   
   public boolean contains(Object value) {
      if(value == null) {
         throw new IllegalArgumentException("Value must not be null");
      }
      int begin = index(value);
      
      for(int i = 0; i < length; i++) {
         int index = (begin + i) % length; 
         Object current = table[start + index];
      
         if(current == null) {
            return false;
         }
         if(current == value || value.equals(current)) {
            return true;
         }
      }
      return false;
   }
   
   public boolean remove(Object value) {
      if(value == null) {
         throw new IllegalArgumentException("Value must not be null");
      }
      int begin = index(value);
      
      for(int i = 0; i < length; i++) {
         int index = (begin + i) % length; // next slot and wrap around
         Object current = table[start + index];
      
         if(current == null) {
            return false;
         }
         if(current == value || value.equals(current)) {
            table[start + index] = null; // clear the slot
            begin = index + 1;
            size--;
            
            for(int j = 0; j < length; j++) { // make sure we backfill
               index = (begin + j) % length; // slot after match
               current = table[start + index];

               if(current == null) {
                  break;
               }
               table[start + index] = null; // clear slot
               size--; // decrease size
               add(current); // add to fill up space
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
