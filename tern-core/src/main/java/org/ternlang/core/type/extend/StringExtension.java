package org.ternlang.core.type.extend;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StringExtension {

   private final BiFunction<String, Integer, Element> element;

   public StringExtension() {
      this.element = Element::new;
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

   public String head(String value, int count) {
      int length = value.length();

      if(length > count) {
         return value.substring(0, count);
      }
      return value;
   }

   public String tail(String value, int count) {
      int length = value.length();

      if(length > count) {
         return value.substring(length - count, length);
      }
      return value;
   }

   public String each(String value, Consumer<Element> consumer) {
      int length = value.length();

      for(int i = 0; i < length; i++) {
         Element next = element.apply(value, i);
         consumer.accept(next);
      }
      return value;
   }

   public String map(String value, Function<Element, Object> consumer) {
      int length = value.length();

      if(length > 0) {
         StringBuilder builder = new StringBuilder();

         for (int i = 0; i < length; i++) {
            Element next = element.apply(value, i);
            Object result = consumer.apply(next);

            builder.append(result);
         }
         return builder.toString();
      }
      return value;
   }

   public List<Element> elements(String value) {
      int length = value.length();

      if(length > 0) {
         List<Element> list = new ArrayList<>(length);

         for(int i = 0; i < length; i++) {
            Element next = element.apply(value, i);
            list.add(next);
         }
         return list;
      }
      return Collections.emptyList();
   }

   public Iterator<Character> iterator(String value) {
      int length = value.length();

      if(length > 0) {
         List<Character> list = new ArrayList<>(length);

         for(int i = 0; i < length; i++) {
            Character next = value.charAt(i);
            list.add(next);
         }
         return list.iterator();
      }
      return Collections.emptyIterator();
   }

   public String filter(String value, Predicate<Character> predicate) {
      int length = value.length();

      if(length > 0) {
         StringBuilder builder = new StringBuilder();

         for (int i = 0; i < length; i++) {
            char next = value.charAt(i);

            if(predicate.test(next)) {
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

   public static class Element<T> {

      private final String source;
      private final char value;
      private final int index;

      public Element(String source, int index) {
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
}
