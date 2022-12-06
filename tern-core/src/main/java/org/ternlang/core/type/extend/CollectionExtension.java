package org.ternlang.core.type.extend;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

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

   public <T> boolean disjoint(Collection<? extends T> left, Collection<? extends T> right) {
      return Collections.disjoint(left, right);
   }
}
