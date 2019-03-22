package org.ternlang.tree.math;

import java.math.BigInteger;

import org.ternlang.core.convert.ConstraintAdapter;
import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.ValueCache;

public class BigIntegerCalculator extends ValueCalculator {

   private final ConstraintAdapter adapter;

   public BigIntegerCalculator() {
      this.adapter = new ConstraintAdapter();
   }

   @Override
   public Value power(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      Integer second = right.intValue();
      BigInteger result = first.pow(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value add(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      BigInteger second = adapter.createBigInteger(right);
      BigInteger result = first.add(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value subtract(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      BigInteger second = adapter.createBigInteger(right);
      BigInteger result = first.subtract(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value divide(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      BigInteger second = adapter.createBigInteger(right);
      BigInteger result = first.divide(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value multiply(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      BigInteger second = adapter.createBigInteger(right);
      BigInteger result = first.multiply(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value modulus(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      BigInteger second = adapter.createBigInteger(right);
      BigInteger result = first.mod(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value shiftLeft(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      Integer second = right.intValue();
      BigInteger result = first.shiftLeft(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value shiftRight(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      Integer second = right.intValue();
      BigInteger result = first.shiftRight(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value unsignedShiftRight(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      Integer second = right.intValue();
      BigInteger result = first.shiftRight(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value and(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      BigInteger second = adapter.createBigInteger(right);
      BigInteger result = first.and(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value or(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      BigInteger second = adapter.createBigInteger(right);
      BigInteger result = first.or(second);

      return ValueCache.getBigInteger(result);
   }

   @Override
   public Value xor(Number left, Number right) {
      BigInteger first = adapter.createBigInteger(left);
      BigInteger second = adapter.createBigInteger(right);
      BigInteger result = first.xor(second);

      return ValueCache.getBigInteger(result);
   }
}
