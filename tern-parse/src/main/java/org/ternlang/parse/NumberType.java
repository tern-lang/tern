package org.ternlang.parse;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public enum NumberType {
   INTEGER {
      @Override
      public Number convert(Number number) {
         long value = number.longValue();
         
         if(value >= 0 && value <= MAX_VALUE) {
            return number.intValue();
         }
         if(value <= 0 && value >= MIN_VALUE) {
            return number.intValue();
         }
         return value; // integer as a default may need promotion
      }         
   },
   DOUBLE {
      @Override
      public Number convert(Number number) {
         return number.doubleValue();
      }
   },
   FLOAT {
      @Override
      public Number convert(Number number) {
         return number.floatValue();
      }
   },
   LONG {
      @Override
      public Number convert(Number number) {
         return number.longValue();
      }
   },
   SHORT {
      @Override
      public Number convert(Number number) {
         return number.shortValue();
      }
   },
   BYTE {
      @Override
      public Number convert(Number number) {
         return number.byteValue();
      }
   };
   
   public abstract Number convert(Number number);
}