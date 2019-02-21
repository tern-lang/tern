package org.ternlang.tree.collection;

import static java.lang.Integer.MAX_VALUE;

import java.util.Iterator;

public class Sequence implements Iterable<Number> {

   private final boolean forward;
   private final long first;
   private final long last;
   
   public Sequence(long first, long last, boolean forward) {
      this.forward = forward;
      this.first = first;
      this.last = last;
   }
   
   @Override
   public Iterator<Number> iterator() {
      if(forward) {
         return new ForwardIterator(first, last, last <= MAX_VALUE);
      }
      return new ReverseIterator(last, first, last <= MAX_VALUE);
   }

   private static class ForwardIterator implements Iterator<Number> {

      private boolean integer;
      private long first;
      private long last;
      
      public ForwardIterator(long first, long last, boolean integer) {
         this.integer = integer;
         this.first = first;
         this.last = last;
      }

      @Override
      public boolean hasNext() {
         return first <= last;
      }

      @Override
      public Number next() {
         if(first <= last) {
            long next = first++;

            if(integer) {
               return (int)next;
            }
            return next;
         }
         return null;
      }
      
      @Override
      public void remove() {
         throw new UnsupportedOperationException("Illegal modification of range");
      }
   }

   private static class ReverseIterator implements Iterator<Number> {

      private boolean integer;
      private long first;
      private long last;

      public ReverseIterator(long first, long last, boolean integer) {
         this.integer = integer;
         this.first = first;
         this.last = last;
      }

      @Override
      public boolean hasNext() {
         return first >= last;
      }

      @Override
      public Number next() {
         if(first >= last) {
            long next = first--;

            if(integer) {
               return (int)next;
            }
            return next;
         }
         return null;
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("Illegal modification of range");
      }
   }
}