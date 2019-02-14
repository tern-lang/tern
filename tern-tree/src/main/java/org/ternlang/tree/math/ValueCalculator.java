package org.ternlang.tree.math;

import org.ternlang.core.variable.Value;

public abstract class ValueCalculator implements NumericCalculator {
   
   public static final ValueCalculator INTEGER = new IntegerCalculator();
   public static final ValueCalculator LONG = new LongCalculator();
   public static final ValueCalculator FLOAT = new FloatCalculator();
   public static final ValueCalculator DOUBLE = new DoubleCalculator();
   public static final ValueCalculator SHORT = new ShortCalculator();
   public static final ValueCalculator BYTE = new ByteCalculator();
   
   public Value replace(Value left, Value right) {
      return right;
   }

   public Value coalesce(Value left, Value right) {
      Object primary = left.getValue();
      Object secondary = right.getValue();
      
      return primary == null ? right : left;
   }
   
   public Value power(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return power(primary, secondary);
   }
   
   public Value add(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return add(primary, secondary);
   }
   
   public Value subtract(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return subtract(primary, secondary);
   }
   
   public Value divide(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return divide(primary, secondary);
   }
   
   public Value multiply(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return multiply(primary, secondary);
   }
   
   public Value modulus(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return modulus(primary, secondary);
   }
   
   public Value shiftLeft(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return shiftLeft(primary, secondary);
   }
   
   public Value shiftRight(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return shiftRight(primary, secondary);
   }
   
   public Value unsignedShiftRight(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return unsignedShiftRight(primary, secondary);
   }
   
   public Value and(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return and(primary, secondary);
   }
   
   public Value or(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return or(primary, secondary);
   }
   
   public Value xor(Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return xor(primary, secondary);
   }
}
