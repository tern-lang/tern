package org.ternlang.tree.math;

import org.ternlang.core.variable.Value;

public interface NumberCalculator {
   Value power(Number left, Number right);
   Value add(Number left, Number right);
   Value subtract(Number left, Number right);
   Value divide(Number left, Number right);
   Value multiply(Number left, Number right);
   Value modulus(Number left, Number right);
   Value shiftLeft(Number left, Number right);
   Value shiftRight(Number left, Number right);
   Value unsignedShiftRight(Number left, Number right);
   Value and(Number left, Number right);
   Value or(Number left, Number right);
   Value xor(Number left, Number right);
}