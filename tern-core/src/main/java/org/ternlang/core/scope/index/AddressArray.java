package org.ternlang.core.scope.index;

import java.util.Arrays;
import java.util.Iterator;

import org.ternlang.common.EmptyIterator;
import org.ternlang.core.variable.Value;

public class AddressArray<T> implements Iterable<T> {

   private Object[] array;

   public AddressArray() {
      this(0);
   }
   
   public AddressArray(int count) {
      this.array = new Value[count];
   }

   @Override
   public Iterator<T> iterator() {
      if(array.length > 0) {
         return new LocalIterator<T>(array);
      }
      return new EmptyIterator<T>();
   }
   
   public T get(Address address) {
      int index = address.getOffset();
      
      if(index < array.length && index >= 0) {
         return (T)array[index];
      }
      return null;
   }
   
   public void set(Address address, T value) {
      String name = address.getName();
      int index = address.getOffset();   
      
      if(value == null) {
         throw new IllegalStateException("Element for '" + name + "' at index " + index + " is null");
      }  
      if(index >= array.length) {
         Object[] copy = new Object[index == 0 ? 2 : index * 2];
         
         for(int i = 0; i < array.length; i++) {
            copy[i] = array[i];
         }
         array = copy;
      }
      array[index] = value;
   }
   
   @Override
   public String toString() {
      return Arrays.toString(array);
   }
   
   private static class LocalIterator<T> implements Iterator<T> {
      
      private Object[] table;
      private Object local;
      private int index;

      public LocalIterator(Object[] table) {
         this.table = table;
      }
      
      @Override
      public boolean hasNext() {
         while(local == null) {
            if(index >= table.length) {
               break;
            }
            local = table[index++];
         }
         return local != null;
      }

      @Override
      public T next() {
         Object next = null;
         
         if(hasNext()) {
            next = local;
            local = null;
         }
         return (T)next;
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("Illegal modification");
      }
   }
}
