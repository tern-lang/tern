package org.ternlang.common.functional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Spread<T> extends Iterable<T> {

   static <T> Spread<T> empty() {
      return () -> Collections.emptyIterator();
   }

   static <T> Spread<T> of(T element) {
      return () -> Arrays.asList(element).iterator();
   }

   static <T> Spread<T> of(T[] elements) {
      return () -> Arrays.asList(elements).iterator();
   }

   static <T> Spread<T> of(Collection<T> elements) {
      return () -> elements.iterator();
   }

   static <T> Spread<T> of(Iterator<T> elements) {
      return () -> elements;
   }

   default Stream<T> stream() {
      return StreamSupport.stream(spliterator(), false);
   }

   default Set<T> toSet() {
      return stream().collect(Collectors.toSet());
   }

   default List<T> toList() {
      return stream().collect(Collectors.toList());
   }

   default <K> Map<K, T> toMap(Function<T, K> extractor) {
      return stream().collect(Collectors.toMap(extractor, Function.identity()));
   }
}
