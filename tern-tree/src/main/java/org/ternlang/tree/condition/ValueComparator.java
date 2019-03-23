package org.ternlang.tree.condition;

import static org.ternlang.tree.condition.ComparisonType.CHARACTER;
import static org.ternlang.tree.condition.ComparisonType.COMPARABLE;
import static org.ternlang.tree.condition.ComparisonType.DECIMAL;
import static org.ternlang.tree.condition.ComparisonType.INTEGER;
import static org.ternlang.tree.condition.ComparisonType.OBJECT;
import static org.ternlang.tree.condition.ComparisonType.STRING;
import static org.ternlang.tree.condition.ComparisonType.resolveType;

import org.ternlang.core.variable.Value;

public enum ValueComparator {
   INTEGER_INTEGER {
      @Override
      public int compare(Value left, Value right) {
         long primary = left.getLong();
         long secondary = right.getLong();

         if(primary != secondary) {
            return primary > secondary ? 1 : -1;
         }
         return 0;
      }
   },
   DECIMAL_DECIMAL {
      @Override
      public int compare(Value left, Value right) {
         double primary = left.getDouble();
         double secondary = right.getDouble();

         return Double.compare(primary, secondary);
      }
   },
   INTEGER_CHARACTER {
      @Override
      public int compare(Value left, Value right) {
         int primary = left.getInteger();
         int secondary = right.getCharacter();

         if(primary != secondary) {
            return primary > secondary ? 1 : -1;
         }
         return 0;
      }
   },
   CHARACTER_INTEGER {
      @Override
      public int compare(Value left, Value right) {
         char primary = left.getCharacter();
         int secondary = right.getInteger();

         if(primary != secondary) {
            return primary > secondary ? 1 : -1;
         }
         return 0;
      }
   },
   STRING_CHARACTER {
      @Override
      public int compare(Value left, Value right) {
         String primary = left.getString();
         int length = primary.length();
         
         if(length > 0) {
            char secondary = right.getCharacter();
            char value = primary.charAt(0);

            if(value != secondary) {
               return value > secondary ? 1 : -1;
            }
            return 0;
         }
         return -1;
      }
   },
   CHARACTER_STRING {
      @Override
      public int compare(Value left, Value right) {
         String secondary = right.getString();
         int length = secondary.length();
         
         if(length > 0) {
            char primary = left.getCharacter();
            char value = secondary.charAt(0);

            if(primary != value) {
               return primary > value ? 1 : -1;
            }
            return 0;
         }
         return 1;
      }
   },
   COMPARABLE_COMPARABLE{
      @Override
      public int compare(Value left, Value right) {
         Comparable primary = left.getValue();
         Comparable secondary = right.getValue();

         return primary.compareTo(secondary);
      }
   },
   OBJECT_OBJECT{
      @Override
      public int compare(Value left, Value right) {
         Object primary = left.getValue();
         Object secondary = right.getValue();

         if(primary != secondary) {
            if(primary != null && secondary != null) {
               if(primary.equals(secondary)) {
                  return 0;
               }
               return -1;
            }
            return primary == null ? -1 : 1;
         }
         return 0;
      }
   };

   public abstract int compare(Value left, Value right);
   
   public static ValueComparator resolveComparator(Value left, Value right) {
      ComparisonType primary = resolveType(left);
      ComparisonType secondary = resolveType(right);

      return TABLE[TYPES.length * primary.index + secondary.index];
   }

   private static final ComparisonType[] TYPES = {
      INTEGER,
      DECIMAL,
      CHARACTER,
      STRING,
      COMPARABLE,
      OBJECT
   };

   private static final ValueComparator[] TABLE = {
      INTEGER_INTEGER,
      DECIMAL_DECIMAL,
      INTEGER_CHARACTER,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      DECIMAL_DECIMAL,
      DECIMAL_DECIMAL,
      INTEGER_CHARACTER,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      CHARACTER_INTEGER,
      CHARACTER_INTEGER,
      COMPARABLE_COMPARABLE,
      CHARACTER_STRING,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      STRING_CHARACTER,
      COMPARABLE_COMPARABLE,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      COMPARABLE_COMPARABLE,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT,
      OBJECT_OBJECT
   };
}