package org.ternlang.core.type.extend;

import org.ternlang.common.functional.FoldLeft;
import org.ternlang.common.functional.FoldRight;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SetExtension {

   public SetExtension() {
      super();
   }

   public <T>  Function<T, Set<T>> fill(Set<T> list, int count) {
      return value -> {
         Set<T> copy = new LinkedHashSet<>(list);

         for(int i = 0; i < count; i++) {
            copy.add(value);
         }
         return copy;
      };
   }

   public <T> Set<T> plus(Set<T> list, T value) {
      Set<T> result = new LinkedHashSet<>();

      result.addAll(list);
      result.add(value);

      return result;
   }

   public <T> Set<T> minus(Set<T> list, T value) {
      Set<T> result = new LinkedHashSet<>();

      for(T element : list) {
         if(element != value) {
            result.add(element);
         }
      }
      return result;
   }

   public <T> Set<T> disjoint(Set<T> left, Set<T> right) {
      Set<T> result = new LinkedHashSet<>();

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

   public <T> Set<T> union(Set<T> left, Set<T> right) {
      Set<T> result = new LinkedHashSet<>();

      result.addAll(left);
      result.addAll(right);

      return result;
   }

   public <T> Set<T> intersect(Set<T> left, Set<T> right) {
      Set<T> result = new LinkedHashSet<>();

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

   public <A> FoldLeft<A, A> fold(List<A> list, A value) {
      return operator -> {
         Iterator<A> iterator = list.iterator();
         A result = value;

         while(iterator.hasNext()) {
            A next = iterator.next();
            result = operator.apply(result, next);
         }
         return result;
      };
   }

   public <A, B> FoldLeft<A, B> foldLeft(Set<A> set, B value) {
      return operator -> {
         B result = value;

         for(A element : set) {
            result = operator.apply(result, element);
         }
         return result;
      };
   }

   public <A, B> FoldRight<A, B> foldRight(Set<A> set, B value) {
      return operator -> {
         Deque<A> queue = new ArrayDeque<>();
         B result = value;

         for(A element : set) {
            queue.addFirst(element);
         }
         while(queue.isEmpty()) {
            A next = queue.pollFirst();
            result = operator.apply(next, result);
         }
         return result;
      };
   }

   public <A> List<A> toList(Set<A> set) {
      return new ArrayList<>(set);
   }

   public <A> Queue<A> toQueue(Set<A> set) {
      return new ArrayDeque<>(set);
   }
}
