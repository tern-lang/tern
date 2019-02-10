package tern.tree.math;

import tern.core.variable.Value;
import tern.core.variable.ValueCache;

public class FloatCalculator extends IntegerCalculator {

   @Override
   public Value add(Number left, Number right) {
      float first = left.floatValue();
      float second = right.floatValue();
      
      return ValueCache.getFloat(first + second);
   }

   @Override
   public Value subtract(Number left, Number right) {
      float first = left.floatValue();
      float second = right.floatValue();
      
      return ValueCache.getFloat(first - second);
   }

   @Override
   public Value divide(Number left, Number right) {
      float first = left.floatValue();
      float second = right.floatValue();
      
      return ValueCache.getFloat(first / second);
   }

   @Override
   public Value multiply(Number left, Number right) {
      float first = left.floatValue();
      float second = right.floatValue();
      
      return ValueCache.getFloat(first * second);
   }

   @Override
   public Value modulus(Number left, Number right) {
      float first = left.floatValue();
      float second = right.floatValue();
      
      return ValueCache.getFloat(first % second);
   }
}