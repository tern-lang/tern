package org.ternlang.core.type.extend;

import org.ternlang.common.functional.FoldLeft;
import org.ternlang.common.functional.FoldRight;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SetExtension {

   public SetExtension() {
      super();
   }

   public <T> Function<T, Set<T>> fill(Set<T> list, int count) {
      return value -> {
         Set<T> copy = new LinkedHashSet<>(list);

         for (int i = 0; i < count; i++) {
            copy.add(value);
         }
         return copy;
      };
   }

   public <T> Set<T> plus(Set<T> list, T value) {
      if(value instanceof Collection) {
         return plus(list, (Collection)value);
      }
      return plus(list, Arrays.asList(value));
   }

   public <T> Set<T> plus(Set<T> list, Collection<T> values) {
      Set<T> result = new LinkedHashSet<>();

      result.addAll(list);
      result.addAll(values);

      return result;
   }


   public <T> Set<T> minus(Set<T> list, T value) {
      if(value instanceof Collection) {
         return minus(list, (Collection)value);
      }
      return minus(list, Arrays.asList(value));
   }

   public <T> Set<T> minus(Set<T> list, Collection<T> values) {
      Set<T> result = new LinkedHashSet<>();

      for (T element : list) {
         if (!values.contains(element)) {
            result.add(element);
         }
      }
      return result;
   }

   public <T> Set<T> disjoint(Set<T> left, Collection<T> right) {
      Set<T> result = new LinkedHashSet<>();

      for (T value : left) {
         if (!right.contains(value)) {
            result.add(value);
         }
      }
      for (T value : right) {
         if (!left.contains(value)) {
            result.add(value);
         }
      }
      return result;
   }

   public <T> Set<T> union(Set<T> left, Collection<T> right) {
      Set<T> result = new LinkedHashSet<>();

      result.addAll(left);
      result.addAll(right);

      return result;
   }

   public <T> Set<T> intersect(Set<T> left, Collection<T> right) {
      Set<T> result = new LinkedHashSet<>();

      for (T value : left) {
         if (right.contains(value)) {
            result.add(value);
         }
      }
      for (T value : right) {
         if (left.contains(value)) {
            result.add(value);
         }
      }
      return result;
   }

   public <A> Set<A> filter(Set<A> list, Predicate<A> filter) {
      int length = list.size();

      if (length > 0) {
         return list.stream()
              .filter(filter)
              .collect(Collectors.toSet());
      }
      return Collections.emptySet();
   }

   public <A, B> Set<B> map(Set<A> list, Function<A, B> function) {
      int length = list.size();

      if (length > 0) {
         return list.stream()
              .map(function)
              .collect(Collectors.toSet());
      }
      return Collections.emptySet();
   }

   public <A> FoldLeft<A, A, A> fold(List<A> set, A value) {
      return operator -> {
         Iterator<A> iterator = set.iterator();
         A result = value;

         while (iterator.hasNext()) {
            A next = iterator.next();
            result = operator.apply(result, next);
         }
         return result;
      };
   }

   public <A, B> FoldLeft<A, B, B> foldLeft(Set<A> set, B value) {
      return operator -> {
         B result = value;

         for (A element : set) {
            result = operator.apply(result, element);
         }
         return result;
      };
   }

   public <A, B> FoldRight<A, B, B> foldRight(Set<A> set, B value) {
      return operator -> {
         Iterator<A> iterator = set.iterator();
         B result = value;

         if (iterator.hasNext()) {
            Deque<A> queue = new ArrayDeque<>();

            while (iterator.hasNext()) {
               A next = iterator.next();
               queue.addFirst(next);
            }
            while (queue.isEmpty()) {
               A next = queue.pollFirst();
               result = operator.apply(next, result);
            }
         }
         return result;
      };
   }

   public <A> FoldLeft<A, A, Set<A>> scan(Set<A> set, A value) {
      return operator -> {
         Iterator<A> iterator = set.iterator();
         A result = value;

         if (iterator.hasNext()) {
            Set<A> results = new LinkedHashSet<>();

            while (iterator.hasNext()) {
               A next = iterator.next();
               result = operator.apply(result, next);
               results.add(result);
            }
            return results;
         }
         return Collections.emptySet();
      };
   }

   public <A, B> FoldLeft<A, B, Set<B>> scanLeft(Set<A> set, B value) {
      return operator -> {
         Iterator<A> iterator = set.iterator();
         B result = value;

         if (iterator.hasNext()) {
            Set<B> results = new LinkedHashSet<>();

            while (iterator.hasNext()) {
               A next = iterator.next();
               result = operator.apply(result, next);
               results.add(result);
            }
            return results;
         }
         return Collections.emptySet();
      };
   }

   public <A, B> FoldRight<A, B, Set<B>> scanRight(Set<A> set, B value) {
      return operator -> {
         Iterator<A> iterator = set.iterator();
         B result = value;

         if (iterator.hasNext()) {
            Deque<A> queue = new ArrayDeque<>();
            Set<B> results = new LinkedHashSet<>();

            while (iterator.hasNext()) {
               A next = iterator.next();
               queue.addFirst(next);
            }
            while (queue.isEmpty()) {
               A next = queue.pollFirst();
               result = operator.apply(next, result);
               results.add(result);
            }
            return results;
         }
         return Collections.emptySet();
      };
   }

   public <K, V> Map<K, V> toMap(Set<V> set, Function<V, K> extractor) {
      Iterator<V> iterator = set.iterator();

      if (iterator.hasNext()) {
         Map<K, V> map = new LinkedHashMap<>();

         while (iterator.hasNext()) {
            V value = iterator.next();
            K key = extractor.apply(value);

            map.put(key, value);
         }
         return map;
      }
      return Collections.emptyMap();
   }

   public <A> List<A> toList(Set<A> set) {
      return new ArrayList<>(set);
   }

   public <A> Queue<A> toQueue(Set<A> set) {
      return new ArrayDeque<>(set);
   }
}
