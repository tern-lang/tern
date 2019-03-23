package org.ternlang.tree.math;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.ConstraintAdapter;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.ValueCache;

public enum NumberType {
   BIG_DECIMAL(ValueCalculator.BIG_DECIMAL, Constraint.BIG_DECIMAL, 0){
      @Override
      public Value convert(Number number) {
         BigDecimal value = adapter.createBigDecimal(number);
         return ValueCache.getBigDecimal(value);
      }
   },
   BIG_INTEGER(ValueCalculator.BIG_INTEGER, Constraint.BIG_INTEGER, 1){
      @Override
      public Value convert(Number number) {
         BigInteger value = adapter.createBigInteger(number);
         return ValueCache.getBigInteger(value);
      }
   },
   DOUBLE(ValueCalculator.DOUBLE, Constraint.DOUBLE, 2){
      @Override
      public Value convert(Number number) {
         Double value = adapter.createDouble(number);
         return ValueCache.getDouble(value);
      }
   },
   LONG(ValueCalculator.LONG, Constraint.LONG, 3){
      @Override
      public Value convert(Number number) {
         Long value = adapter.createLong(number);
         return ValueCache.getLong(value);
      }
   },
   FLOAT(ValueCalculator.FLOAT, Constraint.FLOAT, 4){
      @Override
      public Value convert(Number number) {
         Float value = adapter.createFloat(number);
         return ValueCache.getFloat(value);
      }
   },
   INTEGER(ValueCalculator.INTEGER, Constraint.INTEGER, 5){
      @Override
      public Value convert(Number number) {
         Integer value = adapter.createInteger(number);
         return ValueCache.getInteger(value);
      }
   },
   CHARACTER(ValueCalculator.INTEGER, Constraint.INTEGER, 6){
      @Override
      public Value convert(Number number) {
         Integer value = adapter.createInteger(number);
         return ValueCache.getInteger(value);
      }
   },
   SHORT(ValueCalculator.SHORT, Constraint.SHORT, 7){
      @Override
      public Value convert(Number number) {
         Short value = adapter.createShort(number);
         return ValueCache.getShort(value);
      }
   },
   BYTE(ValueCalculator.BYTE, Constraint.BYTE, 8){
      @Override
      public Value convert(Number number) {
         Byte value = adapter.createByte(number);
         return ValueCache.getByte(value);
      }
   };

   public final ValueCalculator calculator;
   public final ConstraintAdapter adapter;
   public final Constraint constraint;
   public final int index;

   private NumberType(ValueCalculator calculator, Constraint constraint, int index) {
      this.adapter = new ConstraintAdapter();
      this.calculator = calculator;
      this.constraint = constraint;
      this.index = index;
   }

   public Value increment(Number number) {
      return calculator.add(number, 1);
   }

   public Value decrement(Number number) {
      return calculator.subtract(number, 1);
   }

   public abstract Value convert(Number number);

   public static NumberType resolveType(Type type) {
      if(type != null) {
         Class real = type.getType();
         return resolveType(real);
      }
      return DOUBLE;
   }

   public static NumberType resolveType(Value value) {
      if(value != null) {
         Class type = value.getType();
         return resolveType(type);
      }
      return DOUBLE;
   }

   public static NumberType resolveType(Number number) {
      if(number != null) {
         Class type = number.getClass();
         return resolveType(type);
      }
      return DOUBLE;
   }

   public static NumberType resolveType(Class type) {
      if (Integer.class == type) {
         return INTEGER;
      }
      if (Double.class == type) {
         return DOUBLE;
      }
      if (Long.class == type) {
         return LONG;
      }
      if (Float.class == type) {
         return FLOAT;
      }
      if (Character.class == type) {
         return CHARACTER;
      }
      if (BigDecimal.class == type) {
         return BIG_DECIMAL;
      }
      if (BigInteger.class == type) {
         return BIG_INTEGER;
      }
      if (Short.class == type) {
         return SHORT;
      }
      if (Byte.class == type) {
         return BYTE;
      }
      return DOUBLE;
   }

   public static NumberType resolveType(Type left, Type right) {
      NumberType primary = resolveType(left);
      NumberType secondary = resolveType(right);

      return TABLE[TYPES.length * primary.index + secondary.index];
   }

   public static NumberType resolveType(Value left, Value right) {
      NumberType primary = resolveType(left);
      NumberType secondary = resolveType(right);

      return TABLE[TYPES.length * primary.index + secondary.index];
   }

   private static final NumberType[] TYPES = {
      BIG_DECIMAL,
      BIG_INTEGER,
      DOUBLE,
      LONG,
      FLOAT,
      INTEGER,
      CHARACTER,
      SHORT,
      BYTE
   };

   private static final NumberType[] TABLE = {
      BIG_DECIMAL,
      BIG_DECIMAL,
      BIG_DECIMAL,
      BIG_DECIMAL,
      BIG_DECIMAL,
      BIG_DECIMAL,
      BIG_DECIMAL,
      BIG_DECIMAL,
      BIG_DECIMAL,
      BIG_DECIMAL,
      BIG_INTEGER,
      BIG_DECIMAL,
      BIG_INTEGER,
      BIG_DECIMAL,
      BIG_INTEGER,
      BIG_INTEGER,
      BIG_INTEGER,
      BIG_INTEGER,
      BIG_DECIMAL,
      BIG_DECIMAL,
      DOUBLE,
      DOUBLE,
      DOUBLE,
      DOUBLE,
      DOUBLE,
      DOUBLE,
      DOUBLE,
      BIG_DECIMAL,
      BIG_INTEGER,
      DOUBLE,
      LONG,
      DOUBLE,
      LONG,
      LONG,
      LONG,
      LONG,
      BIG_DECIMAL,
      BIG_DECIMAL,
      DOUBLE,
      DOUBLE,
      FLOAT,
      FLOAT,
      FLOAT,
      FLOAT,
      FLOAT,
      BIG_DECIMAL,
      BIG_INTEGER,
      DOUBLE,
      LONG,
      FLOAT,
      INTEGER,
      INTEGER,
      INTEGER,
      INTEGER,
      BIG_DECIMAL,
      BIG_INTEGER,
      DOUBLE,
      LONG,
      FLOAT,
      INTEGER,
      INTEGER,
      INTEGER,
      INTEGER,
      BIG_DECIMAL,
      BIG_INTEGER,
      DOUBLE,
      LONG,
      FLOAT,
      INTEGER,
      INTEGER,
      SHORT,
      SHORT,
      BIG_DECIMAL,
      BIG_INTEGER,
      DOUBLE,
      LONG,
      FLOAT,
      INTEGER,
      INTEGER,
      SHORT,
      BYTE
   };
}