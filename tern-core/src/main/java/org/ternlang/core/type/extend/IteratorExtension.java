package org.ternlang.core.type.extend;

import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.ternlang.common.Consumer;

public class IteratorExtension {

   public IteratorExtension() {
      super();
   }
   
   public <T> Iterator<T> limit(Iterator<T> iterator, int limit) {
      return new LimitIterator<T>(iterator, limit);
   }

   public <T> Iterator<T> filter(Iterator<T> iterator, Consumer<T, Boolean> filter) {
      return new FilterIterator<T>(iterator, filter);
   }

   public <T> Iterator<T> reverse(Iterator<T> iterator) {
      List<T> list = new ArrayList<T>();
      
      while(iterator.hasNext()) {
         T value = iterator.next();
         list.add(value);
      }
      if(!list.isEmpty()) {
         Collections.reverse(list);
      }
      return list.iterator();
   }

   public <T> Iterator<T> shuffle(Iterator<T> iterator) {
      List<T> list = new ArrayList<T>();

      while(iterator.hasNext()) {
         T value = iterator.next();
         list.add(value);
      }
      if(!list.isEmpty()) {
         Collections.shuffle(list);
      }
      return list.iterator();
   }

   public <T> Iterator<T> shuffle(Iterator<T> iterator, Random random) {
      List<T> list = new ArrayList<T>();

      while(iterator.hasNext()) {
         T value = iterator.next();
         list.add(value);
      }
      if(!list.isEmpty()) {
         Collections.shuffle(list, random);
      }
      return list.iterator();
   }

   public <T> Set<T> toSet(Iterator<T> iterator) {
      Set<T> list = new LinkedHashSet<>();
      
      while(iterator.hasNext()) {
         T value = iterator.next();
         list.add(value);
      }
      return list;
   }

   public <T> List<T> toList(Iterator<T> iterator) {
      List<T> list = new ArrayList<T>();

      while(iterator.hasNext()) {
         T value = iterator.next();
         list.add(value);
      }
      return list;
   }

   private static class FilterIterator<T> implements Iterator<T> {
      
      private Consumer<T, Boolean> filter;
      private Iterator<T> iterator;
      private T next;
      
      public FilterIterator(Iterator<T> iterator, Consumer<T, Boolean> filter) {
         this.iterator = iterator;
         this.filter = filter;
      }

      @Override
      public boolean hasNext() {
         if(next == null) {
            while(iterator.hasNext()) {
               T value = iterator.next();
               Object accept = filter.consume(value);
               
               if(TRUE.equals(accept)) {
                  next = value;
                  return true;
               }
            }
            return false;
         }
         return true;
      }

      @Override
      public T next() {
         if(hasNext()) {
            T result = next;
            next = null;
            return result;
         }
         return null;
      }

      @Override
      public void remove() {
         iterator.remove();
      }
   }
   
   private static class LimitIterator<T> implements Iterator<T> {
      
      private Iterator<T> iterator;
      private int limit;
      
      public LimitIterator(Iterator<T> iterator, int limit) {
         this.iterator = iterator;
         this.limit = limit;
      }

      @Override
      public boolean hasNext() {
         return limit > 0 && iterator.hasNext();
      }

      @Override
      public T next() {
         if(limit-- > 0) {
            return iterator.next();
         }
         return null;
      }

      @Override
      public void remove() {
         iterator.remove();
      }
   }
}
