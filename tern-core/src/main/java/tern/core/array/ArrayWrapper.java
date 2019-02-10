package tern.core.array;

import java.util.AbstractList;
import java.util.RandomAccess;

import tern.core.error.InternalArgumentException;

public abstract class ArrayWrapper<T> extends AbstractList<T> implements RandomAccess {

   public int length() {
      return size();
   }
   
   @Override
   public boolean add(T element) {
      throw new InternalArgumentException("Array cannot be resized");
   }
   
   @Override
   public void add(int index, T element) {
      throw new InternalArgumentException("Array cannot be resized");
   }

   @Override
   public boolean contains(Object o) {
      int index = indexOf(o);

      if (index == -1) {
         return false;
      }
      return true;

   }
}