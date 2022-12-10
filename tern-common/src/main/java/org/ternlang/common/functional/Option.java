package org.ternlang.common.functional;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public interface Option<A> extends Iterable<A> {
   A get();
   boolean isDefined();
   boolean isEmpty();
   boolean exists(A value);
   Option<A> filter(Predicate<A> filter);
   Iterator<A> iterator();
   <B> Fold<A, B> fold(B value);
   <B> FoldLeft<A, B> foldLeft(B value);
   <B> FoldRight<A, B> foldRight(B value);
   Iterable<A> take(int count);
   Iterable<A> takeRight(int count);
   Iterable<A> takeWhile(Predicate<A> filter);
   List<A> toList();
}
