package tern.common;

public interface Stack<T> extends Iterable<T>{
   T pop();
   T peek();
   T get(int index);
   void push(T value);
   boolean contains(T value);
   boolean isEmpty();
   int size();
   void clear();
}