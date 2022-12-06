package org.ternlang.core.type.extend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ListExtension {

   private final BiFunction<List, Integer, Element> element;

   public ListExtension() {
      this.element = Element::new;
   }

   public <T> List<T> fill(List<T> list, T value) {
      Collections.fill(list, value);
      return list;
   }

   public <T> List<T> fill(List<T> list, T value, int count) {
      for(int i = 0; i < count; i++) {
         list.add(i, value);
      }
      return list;
   }

   public <T> List<T> fill(List<T> list, T value, int index, int count) {
      for(int i = index; i < count; i++) {
         list.add(i, value);
      }
      return list;
   }

   public <T> List<T> reverse(List<T> list) {
      Collections.reverse(list);
      return list;
   }

   public <T> List<T> shuffle(List<T> list) {
      Collections.shuffle(list);
      return list;
   }

   public <T> List<T> shuffle(List<T> list, Random random) {
      Collections.shuffle(list, random);
      return list;
   }

   public <T> Integer search(List<? extends Comparable<T>> list, T value) {
      return Collections.binarySearch(list, value);
   }

   public <T> Integer search(List<T> list, T value, Comparator<T> comparator) {
      return Collections.binarySearch(list, value, comparator);
   }

   public <T> List<T> replace(List<T> list, T from, T to) {
      Collections.replaceAll(list, from, to);
      return list;
   }

   public <T> Set<T> distinct(List<T> list) {
      int length = list.size();

      if(length > 0) {
         Set<T> set = new LinkedHashSet<>(length);

         for(int i = 0; i < length; i++) {
            T next = list.get(i);
            set.add(next);
         }
         return set;
      }
      return Collections.emptySet();
   }

   public <T> List<T> head(List<T> list, int count) {
      int length = list.size();

      if(length > count) {
         return list.subList(0, count);
      }
      return list;
   }

   public <T> List<T> tail(List<T> list, int count) {
      int length = list.size();

      if(length > count) {
         return list.subList(length - count, length);
      }
      return list;
   }

   public <T> List<T> each(List<T> list, Consumer<Element<T>> consumer) {
      int count = list.size();

      for(int i = 0; i < count; i++) {
         Element next = element.apply(list, i);
         consumer.accept(next);
      }
      return list;
   }

   public <T> List<T> map(List<T> list, Function<Element<T>, T> consumer) {
      int count = list.size();

      for(int i = 0; i < count; i++) {
         Element<T> next = element.apply(list, i);
         T update = consumer.apply(next);

         list.set(i, update);
      }
      return list;
   }

   public <T> List<Element<T>> elements(List<T> list) {
      int count = list.size();

      if(count > 0) {
         List<Element<T>> elements = new ArrayList<>(count);

         for (int i = 0; i < count; i++) {
            Element<T> next = element.apply(list, i);
            elements.add(next);
         }
         return elements;
      }
      return Collections.emptyList();
   }

   public <T> List<List<T>> sliding(List<T> list, int size) {
      int count = list.size();

      if(count > size) {
         List<List<T>> elements = new ArrayList<>((count / size) + 1);

         for (int i = 0; i <= count - size; i++) {
            List<T> element = list.subList(i, i + size);
            elements.add(element);
         }
         return elements;
      }
      return Collections.emptyList();
   }

   public static class Element<T> {

      private final List<T> source;
      private final T value;
      private final int index;

      public Element(List<T> source, int index) {
         this.value = source.get(index);
         this.source = source;
         this.index = index;
      }

      public List<T> source() {
         return source;
      }

      public T value() {
         return value;
      }

      public int index() {
         return index;
      }

      @Override
      public String toString() {
         return value + " at " + index;
      }
   }
}
