package org.ternlang.common;

import java.util.Iterator;

public class EmptyIterator<T> implements Iterator<T> {

   @Override
   public boolean hasNext() {
      return false;
   }

   @Override
   public T next() {
      return null;
   }

   @Override
   public void remove() {}
}
