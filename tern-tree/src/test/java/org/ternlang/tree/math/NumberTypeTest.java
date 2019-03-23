package org.ternlang.tree.math;

import java.math.BigDecimal;
import java.math.BigInteger;

import junit.framework.TestCase;
import org.ternlang.core.variable.ValueCache;

public class NumberTypeTest extends TestCase {

   public void testNumberTypes() {
      assertEquals(NumberType.FLOAT, NumberType.resolveType(ValueCache.getInteger(1), ValueCache.getFloat(1)));
      assertEquals(NumberType.DOUBLE, NumberType.resolveType(ValueCache.getDouble(1), ValueCache.getFloat(1)));
      assertEquals(NumberType.INTEGER, NumberType.resolveType(ValueCache.getInteger(1), ValueCache.getShort(1)));
      assertEquals(NumberType.BYTE, NumberType.resolveType(ValueCache.getByte(1), ValueCache.getByte(1)));
      assertEquals(NumberType.SHORT, NumberType.resolveType(ValueCache.getByte(1), ValueCache.getShort(1)));
      assertEquals(NumberType.BIG_DECIMAL, NumberType.resolveType(ValueCache.getBigInteger(BigInteger.ONE), ValueCache.getFloat(1)));
      assertEquals(NumberType.BIG_DECIMAL, NumberType.resolveType(ValueCache.getBigDecimal(BigDecimal.ONE), ValueCache.getBigInteger(BigInteger.ONE)));
      assertEquals(NumberType.BIG_INTEGER, NumberType.resolveType(ValueCache.getBigInteger(BigInteger.ONE), ValueCache.getInteger(1)));
   }

}
