package org.ternlang.tree.math;

import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.ValueCache;

public class LongCalculator extends IntegerCalculator {

   @Override
   public Value add(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(first + second);
   }

   @Override
   public Value subtract(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(first - second);
   }

   @Override
   public Value divide(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(first / second);
   }

   @Override
   public Value multiply(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(first * second);
   }

   @Override
   public Value modulus(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(first % second);
   }

   @Override
   public Value shiftLeft(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getDouble(first << second);
   }

   @Override
   public Value shiftRight(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getDouble(first >> second);
   }

   @Override
   public Value unsignedShiftRight(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getDouble(first >>> second);
   }

   @Override
   public Value and(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getDouble(first & second);
   }

   @Override
   public Value or(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getDouble(first | second);
   }

   @Override
   public Value xor(Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getDouble(first ^ second);
   }
}