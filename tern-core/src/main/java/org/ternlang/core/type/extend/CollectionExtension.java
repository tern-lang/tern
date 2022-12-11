package org.ternlang.core.type.extend;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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
