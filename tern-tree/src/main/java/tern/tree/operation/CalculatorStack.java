package tern.tree.operation;

import java.util.ArrayList;
import java.util.List;

public class CalculatorStack<T> {

   private final List<T> stack;

   public CalculatorStack() {
      this.stack = new ArrayList<T>();
   }      

   public boolean isEmpty() {
      return stack.isEmpty();
   }
   
   public int index(T value) {
      return stack.indexOf(value);
   }
   
   public int depth(T value) {
      int size = stack.size();
      int index = stack.lastIndexOf(value);
      int top = size - 1;
      
      if(index >= 0) {
         return top - index;
      }
      return -1;
   }   

   public boolean contains(T value) {
      if(value != null) {
         return stack.contains(value);
      }
      return false;
   }   

   public boolean push(T value) {
      if(value != null) {
         stack.add(value);
      }
      return true;
   }

   public T pop() {
      int size = stack.size();
      
      if(size > 0) {
         return stack.remove(size-1);
      }
      return null;
   }

   public T peek() {
      int size = stack.size();
      
      if(size > 0) {
         return stack.get(size-1);
      }
      return null;
   }
   
   public int size() {
      return stack.size();
   }

   public void clear() {
      stack.clear();
   }
   
   @Override
   public String toString() {
      return String.valueOf(stack);
   }
}