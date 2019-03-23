package org.ternlang.tree.condition;

import org.ternlang.core.variable.Value;

public enum ComparisonType {
   INTEGER(0),
   DECIMAL(1),
   CHARACTER(2),
   STRING(3),
   COMPARABLE(4),
   OBJECT(5);

   public final int index;

   private ComparisonType(int index) {
      this.index = index;
   }

   public static ComparisonType resolveType(Value value) {
      if(value != null) {
         Class type = value.getType();
         return resolveType(type);
      }
      return OBJECT;
   }

   public static ComparisonType resolveType(Object value) {
      if(value != null) {
         Class type = value.getClass();
         return resolveType(type);
      }
      return OBJECT;
   }

   public static ComparisonType resolveType(Class type) {
      if (type != null) {
         if (type == String.class) {
            return STRING;
         }
         if (type == Integer.class) {
            return INTEGER;
         }
         if (type == Double.class) {
            return DECIMAL;
         }
         if (type == Long.class) {
            return INTEGER;
         }
         if (type == Float.class) {
            return DECIMAL;
         }
         if (type == Character.class) {
            return CHARACTER;
         }
         if (Number.class.isAssignableFrom(type)) {
            return DECIMAL;
         }
         if (Comparable.class.isAssignableFrom(type)) {
            return COMPARABLE;
         }
      }
      return OBJECT;
   }
}
