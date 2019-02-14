package org.ternlang.common;

public class CopyOnWriteSparseArray<T> extends SparseArray<T> {

   public CopyOnWriteSparseArray(int length) {
      super(length);
   }

   public CopyOnWriteSparseArray(int length, int block) {
      super(length, block);
   }
   
   @Override
   protected synchronized Object[] segment(int index) {
      return super.segment(index);
   }
}