package tern.tree.math;

import tern.core.variable.Value;
import tern.core.variable.ValueCache;

public class IntegerCalculator extends ValueCalculator {

   @Override
   public Value add(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first + second);
   }

   @Override
   public Value subtract(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first - second);
   }

   @Override
   public Value divide(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first / second);
   }

   @Override
   public Value multiply(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first * second);
   }

   @Override
   public Value modulus(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first % second);
   }

   @Override
   public Value shiftLeft(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first << second);
   }

   @Override
   public Value shiftRight(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first >> second);
   }

   @Override
   public Value unsignedShiftRight(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first >>> second);
   }

   @Override
   public Value and(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first & second);
   }

   @Override
   public Value or(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first | second);
   }

   @Override
   public Value xor(Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(first ^ second);
   }
   
   @Override
   public Value power(Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      double result = Math.pow(first, second);
      
      return ValueCache.getDouble(result);
   }
}