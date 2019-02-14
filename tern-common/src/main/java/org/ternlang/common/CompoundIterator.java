package org.ternlang.common;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CompoundIterator<T> implements Iterator<T>{

   private Iterator[] iterators;
   private Object next;
   private Set done;
   private int index;
   
   public CompoundIterator(Iterator... iterators) {
      this.done = new HashSet<T>();
      this.iterators = iterators;
   }

   @Override
   public boolean hasNext() {
      if(next == null) {
         while(index < iterators.length) {
            Iterator iterator = iterators[index];
            
            while(iterator.hasNext()) {
               Object value = iterator.next();
               
               if(done.add(value)) {
                  next = value;
                  return true;
               }
            }
            index++;
         }
      }
      return next != null;
   }

   @Override
   public T next() {
      Object local = next;
      
      if(local == null) {
         if(!hasNext()) {
            return null;
         }
         local = next;
      }
      next = null;
      return (T)local;
   }
   
   @Override
   public void remove() {
      throw new UnsupportedOperationException("Remove not supported");
   }
}