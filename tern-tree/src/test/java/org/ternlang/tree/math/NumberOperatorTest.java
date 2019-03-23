package org.ternlang.tree.math;

import java.math.BigDecimal;
import java.math.BigInteger;

import junit.framework.TestCase;
import org.ternlang.core.variable.ValueCache;

public class NumberOperatorTest extends TestCase {
   
   public void testNumberOperators(){
      assertEquals(Float.class, NumberOperator.PLUS.operate(ValueCache.getInteger(1), ValueCache.getFloat(1)).getValue().getClass());
      assertEquals(Float.class, NumberOperator.PLUS.operate(ValueCache.getFloat(1), ValueCache.getFloat(1)).getValue().getClass());
      assertEquals(Double.class, NumberOperator.PLUS.operate(ValueCache.getDouble(1), ValueCache.getFloat(1)).getValue().getClass());
      assertEquals(Double.class, NumberOperator.PLUS.operate(ValueCache.getDouble(1), ValueCache.getDouble(1)).getValue().getClass());
      assertEquals(Integer.class, NumberOperator.PLUS.operate(ValueCache.getByte(1), ValueCache.getByte(1)).getValue().getClass());
      assertEquals(BigInteger.class, NumberOperator.PLUS.operate(ValueCache.getBigInteger(BigInteger.ONE), ValueCache.getByte(1)).getValue().getClass());
      assertEquals(BigInteger.class, NumberOperator.PLUS.operate(ValueCache.getBigInteger(BigInteger.ONE), ValueCache.getBigInteger(BigInteger.ONE)).getValue().getClass());
      assertEquals(BigDecimal.class, NumberOperator.PLUS.operate(ValueCache.getBigDecimal(BigDecimal.ONE), ValueCache.getByte(1)).getValue().getClass());
   }
}
