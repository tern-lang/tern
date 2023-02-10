package org.ternlang.common.functional;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class None<A> implements Option<A> {

   @Override
   public A get() {
      throw new NoSuchElementException("No value present");
   }

   @Override
   public boolean isDefined() {
      return false;
   }

   @Override
   public boolean isEmpty() {
      return true;
   }

   @Override
   public boolean exists(A value) {
      return false;
   }

   @Override
   public Option<A> filter(Predicate<A> filter) {
      return this;
   }

   @Override
   public Iterator<A> iterator() {
      return Collections.emptyIterator();
   }

   @Override
   public <B> Fold<A, B> fold(B value) {
      return ignore -> value;
   }

   @Override
   public <B> FoldLeft<A, B, B> foldLeft(B value) {
      return ignore -> value;
   }

   @Override
   public <B> FoldRight<A, B, B> foldRight(B value) {
      return ignore -> value;
   }

   @Override
   public Iterable<A> take(int count) {
      return Collections.emptyList();
   }

   @Override
   public Iterable<A> takeRight(int count) {
      return Collections.emptyList();
   }

   @Override
   public Iterable<A> takeWhile(Predicate<A> filter) {
      return Collections.emptyList();
   }

   @Override
   public List<A> toList() {
      return Collections.emptyList();
   }
}
