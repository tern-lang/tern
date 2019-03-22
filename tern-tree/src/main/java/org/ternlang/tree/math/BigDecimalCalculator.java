package org.ternlang.tree.math;

import java.math.BigDecimal;

import org.ternlang.core.convert.ConstraintAdapter;
import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.ValueCache;

public class BigDecimalCalculator extends BigIntegerCalculator {

   private final ConstraintAdapter adapter;

   public BigDecimalCalculator() {
      this.adapter = new ConstraintAdapter();
   }

   @Override
   public Value power(Number left, Number right) {
      BigDecimal first = adapter.createBigDecimal(left);
      Integer second = right.intValue();
      BigDecimal result = first.pow(second);

      return ValueCache.getBigDecimal(result);
   }

   @Override
   public Value add(Number left, Number right) {
      BigDecimal first = adapter.createBigDecimal(left);
      BigDecimal second = adapter.createBigDecimal(right);
      BigDecimal result = first.add(second);

      return ValueCache.getBigDecimal(result);
   }

   @Override
   public Value subtract(Number left, Number right) {
      BigDecimal first = adapter.createBigDecimal(left);
      BigDecimal second = adapter.createBigDecimal(right);
      BigDecimal result = first.subtract(second);

      return ValueCache.getBigDecimal(result);
   }

   @Override
   public Value divide(Number left, Number right) {
      BigDecimal first = adapter.createBigDecimal(left);
      BigDecimal second = adapter.createBigDecimal(right);
      BigDecimal result = first.divide(second);

      return ValueCache.getBigDecimal(result);
   }

   @Override
   public Value multiply(Number left, Number right) {
      BigDecimal first = adapter.createBigDecimal(left);
      BigDecimal second = adapter.createBigDecimal(right);
      BigDecimal result = first.multiply(second);

      return ValueCache.getBigDecimal(result);
   }
}
