package org.ternlang.core.type.extend;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class StringExtension {

   public StringExtension() {
      super();
   }

   public Character get(String value, int index) {
      return value.charAt(index);
   }

   public String distinct(String value) {
      int length = value.length();

      if(length > 1) {
         StringBuilder builder = new StringBuilder(length);
         Set<Character> values = new HashSet<>();

         for(int i = 0; i < length; i++) {
            char next = value.charAt(i);

            if(values.add(next)) {
               builder.append(next);
            }
         }
         return builder.toString();
      }
      return value;
   }

   public List<String> sliding(String value, int size) {
      int length = value.length();

      if(length > size) {
         List<String> elements = new ArrayList<>((length / size) + 1);

         for (int i = 0; i <= length - size; i++) {
            String element = value.substring(i, i + size);
            elements.add(element);
         }
         return elements;
      }
      return Collections.emptyList();
   }

   public String reverse(String value) {
      int length = value.length();

      if(length > 1) {
         StringBuilder builder = new StringBuilder(length);

         for(int i = length - 1; i >= 0; i--) {
            char next = value.charAt(i);
            builder.append(next);
         }
         return builder.toString();
      }
      return value;
   }

   public String drop(String value, int count) {
      int length = value.length();

      if (length > count) {
         return value.substring(count, length);
      }
      return value;
   }

   public String dropRight(String value, int count) {
      int length = value.length();

      if (length > count) {
         return value.substring(0, length - count);
      }
      return value;
   }

   public String dropWhile(String value, Predicate<Character> filter) {
      int length = value.length();

      if (length > 0) {
         for(int i = 0; i < length; i++) {
            Character next = value.charAt(i);

            if(!filter.test(next)) {
               return value.substring(i, length);
            }
         }
         return "";
      }
      return value;
   }

   public String take(String value, int count) {
      int length = value.length();

      if (length > count) {
         return value.substring(0, count);
      }
      return value;
   }

   public String takeRight(String value, int count) {
      int length = value.length();

      if (length > count) {
         return value.substring(length - count, length);
      }
      return value;
   }

   public String takeWhile(String value, Predicate<Character> filter) {
      int length = value.length();

      if (length > 0) {
         for(int i = 0; i < length; i++) {
            Character next = value.charAt(i);

            if(!filter.test(next)) {
               return value.substring(0, i);
            }
         }
      }
      return value;
   }

   public String map(String value, Function<Character, Object> consumer) {
      int length = value.length();

      if(length > 0) {
         StringBuilder builder = new StringBuilder();

         for (int i = 0; i < length; i++) {
            char next = value.charAt(i);
            Object result = consumer.apply(next);

            builder.append(result);
         }
         return builder.toString();
      }
      return value;
   }

   public List<ZipOne> zip(String value) {
      int length = value.length();

      if(length > 0) {
         List<ZipOne> list = new ArrayList<>(length);

         for(int i = 0; i < length; i++) {
            ZipOne next = new ZipOne(value, i);
            list.add(next);
         }
         return list;
      }
      return Collections.emptyList();
   }

   public List<ZipMany> zip(String left, String right) {
      int count = Math.min(left.length(), right.length());

      if (count > 0) {
         List<ZipMany> result = new ArrayList<>();
         List<String> sources = new ArrayList<>();

         sources.add(left);
         sources.add(right);

         for (int i = 0; i < count; i++) {
            List<Character> values = new ArrayList<>();
            ZipMany value = new ZipMany(sources, values, i);

            values.add(left.charAt(i));
            values.add(right.charAt(i));
            result.add(value);
         }
         return result;
      }
      return Collections.emptyList();
   }

   public String filter(String value, Predicate<Character> predicate) {
      return filterNot(value, predicate.negate());
   }

   public String filterNot(String value, Predicate<Character> predicate) {
      int length = value.length();

      if(length > 0) {
         StringBuilder builder = new StringBuilder();

         for (int i = 0; i < length; i++) {
            char next = value.charAt(i);

            if(!predicate.test(next)) {
               builder.append(next);
            }
         }
         return builder.toString();
      }
      return value;
   }

   public String sort(String value) {
      int length = value.length();

      if(length > 1) {
         char[] values = value.toCharArray();

         Arrays.sort(values);
         return new String(values);
      }
      return value;
   }

   public <K> Map<K, Character> toMap(String value, Function<Character, K> extractor) {
      int length = value.length();

      if(length > 0) {
         Map<K, Character> map = new LinkedHashMap<>();

         for(int i = 0; i < length; i++) {
            Character next = value.charAt(i);
            K key = extractor.apply(next);

            map.put(key, next);
         }
         return map;
      }
      return Collections.emptyMap();
   }

   public List<Character> toList(String value) {
      int length = value.length();

      if(length > 0) {
         List<Character> list = new ArrayList<>();

         for(int i = 0; i < length; i++) {
            Character next = value.charAt(i);
            list.add(next);
         }
         return list;
      }
      return Collections.emptyList();
   }

   public Set<Character> toSet(String value) {
      int length = value.length();

      if(length > 0) {
         Set<Character> set = new LinkedHashSet<>();

         for(int i = 0; i < length; i++) {
            Character next = value.charAt(i);
            set.add(next);
         }
         return set;
      }
      return Collections.emptySet();
   }

   public Integer toInteger(String value) {
      return Integer.parseInt(value);
   }

   public Long toLong(String value) {
      return Long.parseLong(value);
   }

   public Double toDouble(String value) {
      return Double.parseDouble(value);
   }

   public Float toFloat(String value) {
      return Float.parseFloat(value);
   }

   public Short toShort(String value) {
      return Short.parseShort(value);
   }

   public Byte toByte(String value) {
      return Byte.parseByte(value);
   }

   public Character toCharacter(String value) {
      return value.charAt(0);
   }

   public URI toURL(String value) {
      return URI.create(value);
   }

   public static class ZipOne<T> {

      private final String source;
      private final char value;
      private final int index;

      public ZipOne(String source, int index) {
         this.value =  source.charAt(index);
         this.source = source;
         this.index = index;
      }

      public String source() {
         return source;
      }

      public char value() {
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


   public static class ZipMany {

      private final List<Character> values;
      private final List<String> sources;
      private final int index;

      public ZipMany(List<String> sources, List<Character> values, int index) {
         this.sources = Collections.unmodifiableList(sources);
         this.values = Collections.unmodifiableList(values);
         this.index = index;
      }

      public int index() {
         return index;
      }

      public Character value(int i) {
         return values.get(i);
      }

      public List<Character> values(int i) {
         return values;
      }

      public String source(int i) {
         return sources.get(i);
      }

      public List<String> sources() {
         return sources;
      }

      @Override
      public String toString() {
         return String.format("%s at %s", values, index);
      }
   }
}
