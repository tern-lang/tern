package org.ternlang.core.type.extend;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

public class CollectionExtension {

   public CollectionExtension() {
      super();
   }

   public <T> int frequency(Collection<T> collection, T value) {
      return Collections.frequency(collection, value);
   }

   public <T extends Comparable<T>> T max(Collection<? extends T> collection) {
      return Collections.max(collection);
   }

   public <T extends Comparable<T>> T max(Collection<? extends T> collection, Comparator<T> comparator) {
      return Collections.max(collection, comparator);
   }

   public <T extends Comparable<T>> T min(Collection<? extends T> collection) {
      return Collections.min(collection);
   }

   public <T extends Comparable<T>> T min(Collection<? extends T> collection, Comparator<T> comparator) {
      return Collections.min(collection, comparator);
   }

   public <K, V> Map<K, V> toMap(Collection<V> set, Function<V, K> extractor) {
      Iterator<V> iterator = set.iterator();

      if(iterator.hasNext()) {
         Map<K, V> map = new LinkedHashMap<>();

         while(iterator.hasNext()) {
            V value = iterator.next();
            K key = extractor.apply(value);

            map.put(key, value);
         }
         return map;
      }
      return Collections.emptyMap();
   }

   public <T> Set<T> toSet(Collection<T> collection) {
      return new LinkedHashSet<>(collection);
   }

   public <T> List<T> toList(Collection<T> collection) {
      return new ArrayList<>(collection);
   }

   public <T> Queue<T> toQueue(Collection<T> collection) {
      return new ArrayDeque<>(collection);
   }
}
