package org.ternlang.tree.math;

import java.math.BigDecimal;

import junit.framework.TestCase;
import org.ternlang.core.variable.ValueCache;

public class NumericOperatorTest extends TestCase {
   
   public void testCalculators(){
      assertEquals(Float.class, NumericOperator.PLUS.operate(ValueCache.getInteger(1), ValueCache.getFloat(1)).getValue().getClass());
      assertEquals(Float.class, NumericOperator.PLUS.operate(ValueCache.getFloat(1), ValueCache.getFloat(1)).getValue().getClass());
      assertEquals(Double.class, NumericOperator.PLUS.operate(ValueCache.getDouble(1), ValueCache.getFloat(1)).getValue().getClass());
      assertEquals(Integer.class, NumericOperator.PLUS.operate(ValueCache.getByte(1), ValueCache.getByte(1)).getValue().getClass());
      assertEquals(BigDecimal.class, NumericOperator.PLUS.operate(ValueCache.getBigDecimal(BigDecimal.ONE), ValueCache.getByte(1)).getValue().getClass());
   }
}
