package org.ternlang.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class ArraySlice<T> implements Array<T> {

   private final T[] array;
   private final int length;
   private final int off;

   public ArraySlice(T[] array) {
      this(array, 0, array.length);
   }

   public ArraySlice(T[] array, int off, int length) {
      this.array = array;
      this.length = length;
      this.off = off;
   }

   @Override
   public Iterator<T> iterator() {
      if(length > 0) {
         return Arrays.asList(array).subList(off, length).iterator();
      }
      return Collections.emptyIterator();
   }

   @Override
   public T get(int index) {
      if(index >= length || index < 0) {
         throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds");
      }
      if(index + off >= array.length) {
         throw new ArrayIndexOutOfBoundsException("Index " + index + " exceeds bounds of array");
      }
      return array[off + index];
   }

   @Override
   public int length() {
      return length;
   }

   @Override
   public String toString() {
      return Arrays.asList(array).subList(off, length).toString();
   }
}
