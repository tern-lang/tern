package org.ternlang.common;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Array<T> extends Iterable<T> {
   T get(int index);
   int length();

   static <T> Array<T> of(T... array) {
      return new ArraySlice<>(array);
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
