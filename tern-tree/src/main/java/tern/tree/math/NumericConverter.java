package tern.tree.math;

import tern.core.variable.Value;
import tern.core.variable.ValueCache;

public enum NumericConverter {
   DOUBLE {
      @Override
      public Value convert(Number number) {
         double value = number.doubleValue();
         return ValueCache.getDouble(value);
      }
      @Override
      public Value increment(Number number) {
         double value = number.doubleValue();
         return ValueCache.getDouble(value + 1.0d);
      }
      @Override
      public Value decrement(Number number) {
         double value = number.doubleValue();
         return ValueCache.getDouble(value - 1.0d);
      }
   },
   LONG {
      @Override
      public Value convert(Number number) {
         long value = number.longValue();
         return ValueCache.getLong(value);
      }
      @Override
      public Value increment(Number number) {
         long value = number.longValue();
         return ValueCache.getLong(value + 1L);
      }
      @Override
      public Value decrement(Number number) {
         long value = number.longValue();
         return ValueCache.getLong(value - 1L);
      }
   },
   FLOAT {
      @Override
      public Value convert(Number number) {
         float value = number.floatValue();
         return ValueCache.getFloat(value);
      }
      @Override
      public Value increment(Number number) {
         float value = number.floatValue();
         return ValueCache.getFloat(value + 1.0f);
      }
      @Override
      public Value decrement(Number number) {
         float value = number.floatValue();
         return ValueCache.getFloat(value - 1.0f);
      }
   },
   INTEGER {
      @Override
      public Value convert(Number number) {
         int value = number.intValue();
         return ValueCache.getInteger(value);
      }
      @Override
      public Value increment(Number number) {
         int value = number.intValue();
         return ValueCache.getInteger(value + 1);
      }
      @Override
      public Value decrement(Number number) {
         int value = number.intValue();
         return ValueCache.getInteger(value - 1);
      }
   },
   SHORT {
      @Override
      public Value convert(Number number) {
         short value = number.shortValue();
         return ValueCache.getShort(value);
      }
      @Override
      public Value increment(Number number) {
         short value = number.shortValue();
         return ValueCache.getShort(value + 1);
      }
      @Override
      public Value decrement(Number number) {
         short value = number.shortValue();
         return ValueCache.getShort(value - 1);
      }
   },
   BYTE {
      @Override
      public Value convert(Number number) {
         byte value = number.byteValue();
         return ValueCache.getByte(value);
      }
      @Override
      public Value increment(Number number) {
         byte value = number.byteValue();
         return ValueCache.getByte(value + 1);
      }
      @Override
      public Value decrement(Number number) {
         byte value = number.byteValue();
         return ValueCache.getByte(value - 1);
      }
   };
   
   public abstract Value convert(Number number);
   public abstract Value increment(Number number);
   public abstract Value decrement(Number number);
   
   public static NumericConverter resolveConverter(Number number) {
      Class type = number.getClass();
      
      if (Double.class == type) {
         return DOUBLE;
      }
      if (Long.class == type) {
         return LONG;
      }
      if (Float.class == type) {
         return FLOAT;
      }
      if (Integer.class == type) {
         return INTEGER;
      }
      if (Short.class == type) {
         return SHORT;
      }
      if (Byte.class == type) {
         return BYTE;
      }
      return DOUBLE;
   }
   
   public static NumericConverter resolveConverter(Value left, Value right) {
      Class primary = left.getType();
      Class secondary = right.getType();

      if (Double.class == primary || Double.class == secondary) {
         return DOUBLE;
      }
      if (Long.class == primary || Long.class == secondary) {
         return LONG;
      }
      if (Float.class == primary || Float.class == secondary) {
         return FLOAT;
      }
      if (Integer.class == primary || Integer.class == secondary) {
         return INTEGER;
      }
      if (Short.class == primary || Short.class == secondary) {
         return SHORT;
      }
      if (Byte.class == primary || Byte.class == secondary) {
         return BYTE;
      }      
      if (Character.class == primary || Character.class == secondary) {
         return INTEGER;
      }
      return DOUBLE;
   }
}