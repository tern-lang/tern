package org.ternlang.core.type.extend;

import org.ternlang.common.functional.FoldLeft;
import org.ternlang.common.functional.FoldRight;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListExtension {

   public ListExtension() {
      super();
   }

   public <T>  Function<T, List<T>> fill(List<T> list, int count) {
      return value -> {
         List<T> copy = new ArrayList<>(list);

         for(int i = 0; i < count; i++) {
            copy.add(value);
         }
         return copy;
      };
   }

   public <T> Function<T, List<T>> fill(List<T> list, int index, int count) {
      return value -> {
         List<T> copy = new ArrayList<>(list);

         for(int i = 0; i < count; i++) {
            copy.add(i + index, value);
         }
         return copy;
      };
   }

   public <T> List<T> reverse(List<T> list) {
      int count = list.size();

      if (count > 0) {
         List<T> copy = new ArrayList<>(list);

         Collections.reverse(copy);
         return copy;
      }
      return list;
   }

   public <T> List<T> shuffle(List<T> list) {
      int count = list.size();

      if (count > 0) {
         List<T> copy = new ArrayList<>(list);

         Collections.shuffle(copy);
         return copy;
      }
      return list;
   }

   public <T> List<T> shuffle(List<T> list, Random random) {
      int count = list.size();

      if (count > 0) {
         List<T> copy = new ArrayList<>(list);

         Collections.shuffle(copy, random);
         return copy;
      }
      return list;
   }

   public <T> List<T> replace(List<T> list, T from, T to) {
      int count = list.size();

      if (count > 0) {
         List<T> copy = new ArrayList<>(list);

         Collections.replaceAll(copy, from, to);
         return copy;
      }
      return list;
   }

   public <T> Integer search(List<? extends Comparable<T>> list, T value) {
      return Collections.binarySearch(list, value);
   }

   public <T> Integer search(List<T> list, T value, Comparator<T> comparator) {
      return Collections.binarySearch(list, value, comparator);
   }

   public <T> List<T> plus(List<T> list, T value) {
      List<T> result = new ArrayList<>();

      result.addAll(list);
      result.add(value);

      return result;
   }

   public <T> List<T> minus(List<T> list, T value) {
      List<T> result = new ArrayList<>();

      for(T element : list) {
         if(element != value) {
            result.add(element);
         }
      }
      return result;
   }

   public <T> List<T> disjoint(List<T> left, List<T> right) {
      List<T> result = new ArrayList<>();

      for(T value : left) {
         if(!right.contains(value)) {
            result.add(value);
         }
      }
      for(T value : right) {
         if(!left.contains(value)) {
            result.add(value);
         }
      }
      return result;
   }

   public <T> List<T> union(List<T> left, List<T> right) {
      List<T> result = new ArrayList<>();

      result.addAll(left);
      result.addAll(right);

      return result;
   }

   public <T> List<T> intersect(List<T> left, List<T> right) {
      List<T> result = new ArrayList<>();

      for(T value : left) {
         if(right.contains(value)) {
            result.add(value);
         }
      }
      for(T value : right) {
         if(left.contains(value)) {
            result.add(value);
         }
      }
      return result;
   }

   public <T> Set<T> distinct(List<T> list) {
      int length = list.size();

      if (length > 0) {
         Set<T> set = new LinkedHashSet<>(length);

         for (int i = 0; i < length; i++) {
            T next = list.get(i);
            set.add(next);
         }
         return set;
      }
      return Collections.emptySet();
   }

   public <T> T head(List<T> list) {
      int length = list.size();

      if (length > 0) {
         return list.get(0);
      }
      return null;
   }

   public <T> T tail(List<T> list) {
      int length = list.size();

      if (length > 0) {
         return list.get(length - 1);
      }
      return null;
   }

   public <A> List<A> filter(List<A> list, Predicate<A> filter) {
      int length = list.size();

      if (length > 0) {
         return list.stream()
              .filter(filter)
              .collect(Collectors.toList());
      }
      return Collections.emptyList();
   }

   public <A> List<A> filterNot(List<A> list, Predicate<A> filter) {
      int length = list.size();

      if (length > 0) {
         return list.stream()
              .filter(filter.negate())
              .collect(Collectors.toList());
      }
      return Collections.emptyList();
   }

   public <A, B> List<B> map(List<A> list, Function<A, B> function) {
      int length = list.size();

      if (length > 0) {
         return list.stream()
              .map(function)
              .collect(Collectors.toList());
      }
      return Collections.emptyList();
   }

   public <T> List<ZipOne<T>> zip(List<T> list) {
      int count = list.size();

      if (count > 0) {
         List<ZipOne<T>> result = new ArrayList<>();

         for (int i = 0; i < count; i++) {
            ZipOne<T> value = new ZipOne<>(list, i);
            result.add(value);
         }
         return result;
      }
      return Collections.emptyList();
   }

   public <A, B> List<ZipMany> zip(List<A> left, List<B> right) {
      int count = Math.min(left.size(), right.size());

      if (count > 0) {
         List<ZipMany> result = new ArrayList<>();
         List<List> sources = new ArrayList<>();

         sources.add(left);
         sources.add(right);

         for (int i = 0; i < count; i++) {
            List<Object> values = new ArrayList<>();
            ZipMany value = new ZipMany(sources, values, i);

            values.add(left.get(i));
            values.add(right.get(i));
            result.add(value);
         }
         return result;
      }
      return Collections.emptyList();
   }

   public <A> List<A> drop(List<A> list, int count) {
      int length = list.size();

      if (length > count) {
         return list.subList(count, length);
      }
      return list;
   }

   public <A> List<A> dropRight(List<A> list, int count) {
      int length = list.size();

      if (length > count) {
         return list.subList(0, length - count);
      }
      return list;
   }

   public <A> List<A> dropWhile(List<A> list, Predicate<A> filter) {
      int length = list.size();

      if (length > 0) {
         for(int i = 0; i < length; i++) {
            A value = list.get(i);

            if(!filter.test(value)) {
               return list.subList(i, length);
            }
         }
      }
      return Collections.emptyList();
   }

   public <A> List<A> take(List<A> list, int count) {
      int length = list.size();

      if (length > count) {
         return list.subList(0, count);
      }
      return list;
   }

   public <A> List<A> takeRight(List<A> list, int count) {
      int length = list.size();

      if (length > count) {
         return list.subList(length - count, length);
      }
      return list;
   }

   public <A> List<A> takeWhile(List<A> list, Predicate<A> filter) {
      int length = list.size();

      if (length > 0) {
         List<A> result = new ArrayList<>();

         for(A value : list) {
            if(!filter.test(value)) {
               return result;
            }
            result.add(value);
         }
         return result;
      }
      return list;
   }

   public <A> FoldLeft<A, A> fold(List<A> list, A value) {
      return operator -> {
         int count = list.size();
         A result = value;

         for (int i = 0; i < count; i++) {
            A next = list.get(i);
            result = operator.apply(result, next);
         }
         return result;
      };
   }

   public <A, B> FoldLeft<A, B> foldLeft(List<A> list, B value) {
      return operator -> {
         int count = list.size();
         B result = value;

         for (int i = 0; i < count; i++) {
            A next = list.get(i);
            result = operator.apply(result, next);
         }
         return result;
      };
   }

   public <A, B> FoldRight<A, B> foldRight(List<A> list, B value) {
      return operator -> {
         int count = list.size();
         B result = value;

         for (int i = count - 1; i >= 0; i--) {
            A next = list.get(i);
            result = operator.apply(next, result);
         }
         return result;
      };
   }

   public <A> int indexWhere(List<A> list, Predicate<A> filter) {
      return indexWhere(list, filter, 0);
   }

   public <A> int indexWhere(List<A> list, Predicate<A> filter, int from) {
      int count = list.size();

      for(int i = from; i < count; i++) {
         A next = list.get(i);

         if(filter.test(next)) {
            return i;
         }
      }
      return -1;
   }

   public <A> boolean containsSlice(List<A> list, List<A> slice) {
      return indexOfSlice(list, slice) != -1;
   }

   public <A> int indexOfSlice(List<A> list, List<A> slice) {
      return indexOfSlice(list, slice, 0);
   }

   public <A> int indexOfSlice(List<A> list, List<A> slice, int from) {
      int count = slice.size();

      if(count > 0) {
         int length = list.size();

         for(int i = from; i <= length - count; i++) {
            List<A> value = list.subList(i, i + count);

            if(value.equals(slice)) {
               return i;
            }
         }
         return -1;
      }
      return from;
   }

   public <T> List<List<T>> sliding(List<T> list, int size) {
      int count = list.size();

      if (count > size) {
         List<List<T>> elements = new ArrayList<>((count / size) + 1);

         for (int i = 0; i <= count - size; i++) {
            List<T> element = list.subList(i, i + size);
            elements.add(element);
         }
         return elements;
      }
      return Collections.emptyList();
   }

   public <A> Set<A> toSet(List<A> list) {
      return new LinkedHashSet<>(list);
   }

   public <A> Queue<A> toQueue(List<A> list) {
      return new ArrayDeque<>(list);
   }

   public static class ZipOne<T> {

      private final List<T> source;
      private final T value;
      private final int index;

      public ZipOne(List<T> source, int index) {
         this.source = Collections.unmodifiableList(source);
         this.value = source.get(index);
         this.index = index;
      }

      public int index() {
         return index;
      }

      public T value() {
         return value;
      }

      public List<T> source() {
         return source;
      }

      @Override
      public String toString() {
         return String.format("%s at %s", value, index);
      }
   }

   public static class ZipMany {

      private final List<List> sources;
      private final List<Object> values;
      private final int index;

      public ZipMany(List<List> sources, List<Object> values, int index) {
         this.sources = Collections.unmodifiableList(sources);
         this.values = Collections.unmodifiableList(values);
         this.index = index;
      }

      public int index() {
         return index;
      }

      public Object value(int i) {
         return values.get(i);
      }

      public List<Object> values(int i) {
         return values;
      }

      public List source(int i) {
         return sources.get(i);
      }

      public List<List> sources() {
         return sources;
      }

      @Override
      public String toString() {
         return String.format("%s at %s", values, index);
      }
   }
}
