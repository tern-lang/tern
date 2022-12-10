package org.ternlang.common.functional;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Some<A> implements Option<A> {

   private final A value;

   public Some(A value) {
      this.value = value;
   }

   @Override
   public A get() {
      return value;
   }

   @Override
   public boolean isDefined() {
      return true;
   }

   @Override
   public boolean isEmpty() {
      return false;
   }

   @Override
   public boolean exists(A other) {
      return value.equals(other);
   }

   @Override
   public Option<A> filter(Predicate<A> filter) {
      return filter.test(value) ? this : new None();
   }

   @Override
   public Iterator<A> iterator() {
      return Collections.singletonList(value).iterator();
   }

   @Override
   public <B> Fold<A, B> fold(B other) {
      return function -> function.apply(value);
   }

   @Override
   public <B> FoldLeft<A, B> foldLeft(B other) {
      return function -> function.apply(other, value);
   }

   @Override
   public <B> FoldRight<A, B> foldRight(B other) {
      return function -> function.apply(value, other);
   }

   @Override
   public Iterable<A> take(int count) {
      return Collections.singletonList(value);
   }

   @Override
   public Iterable<A> takeRight(int count) {
      return Collections.singletonList(value);
   }

   @Override
   public Iterable<A> takeWhile(Predicate<A> filter) {
      return filter.test(value) ? Collections.singletonList(value) : Collections.emptyList();
   }

   @Override
   public List<A> toList() {
      return Collections.singletonList(value);
   }
}
