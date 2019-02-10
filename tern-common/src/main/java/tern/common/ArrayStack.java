package tern.common;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayStack<T> implements Stack<T>{

   private Object[] stack;
   private int count;

   public ArrayStack() {
      this(1024);
   }
   
   public ArrayStack(int capacity) {
      this.stack = new Object[capacity];
   }      

   @Override
   public boolean isEmpty() {
      return count == 0;
   }   
   
   @Override
   public Iterator<T> iterator() {
      return new ArrayIterator(count-1);
   }
   
   @Override
   public T get(int index) {
      if(index < count) {
         return (T)stack[index];
      }
      return null;
   }

   @Override
   public boolean contains(T value) {
      for(int i = 0; i < count; i++) {
         Object next = stack[i];
         
         if(next.equals(value)) {
            return true;
         }
      }
      return false; 
   }   

   @Override
   public void push(T value) {
      int size = stack.length;
      
      if(count >= size) {
         Object[] copy = new Object[size == 0 ? 2 : size * 2];
         
         if(count > 0) {
            System.arraycopy(stack, 0, copy, 0, size);
         }
         stack = copy;
      }
      stack[count++] = value;
   }

   @Override
   public T pop() {
      if(count > 0) {
         int index = count-- -1;
         Object value = stack[index];
         
         if(value != null) {
            stack[index] =null;
         }
         return (T)value;
      }
      return null;
   }

   @Override
   public T peek() {
      if(count > 0) {
         return (T)stack[count -1];
      }
      return null;
   }
   
   @Override
   public int size() {
      return count;
   }

   @Override
   public void clear() {
      count = 0;
   }
   
   @Override
   public String toString() {
      return Arrays.toString(stack);
   }
   
   private class ArrayIterator implements Iterator<T> {
      
      public int index;
      
      public ArrayIterator(int index) {
         this.index = index;
      }
      
      @Override
      public boolean hasNext() {
         if(index >= 0) {
            return true;
         }
         return false;
      }
      
      @Override
      public T next() {
         if(index >= 0) {
            return (T)stack[index--];
         }
         return null;
      }
      
      @Override
      public void remove() {
         throw new UnsupportedOperationException("Remove not supported");
      }
   }
}