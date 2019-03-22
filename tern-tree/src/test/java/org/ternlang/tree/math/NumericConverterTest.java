package org.ternlang.tree.math;

import java.math.BigDecimal;
import java.math.BigInteger;

import junit.framework.TestCase;
import org.ternlang.core.variable.ValueCache;

public class NumericConverterTest extends TestCase {

   public void testConverter() {
      assertEquals(NumericConverter.FLOAT, NumericConverter.resolveConverter(ValueCache.getInteger(1), ValueCache.getFloat(1)));
      assertEquals(NumericConverter.DOUBLE, NumericConverter.resolveConverter(ValueCache.getDouble(1), ValueCache.getFloat(1)));
      assertEquals(NumericConverter.INTEGER, NumericConverter.resolveConverter(ValueCache.getInteger(1), ValueCache.getShort(1)));
      assertEquals(NumericConverter.BYTE, NumericConverter.resolveConverter(ValueCache.getByte(1), ValueCache.getByte(1)));
      assertEquals(NumericConverter.SHORT, NumericConverter.resolveConverter(ValueCache.getByte(1), ValueCache.getShort(1)));
      assertEquals(NumericConverter.BIG_DECIMAL, NumericConverter.resolveConverter(ValueCache.getBigInteger(BigInteger.ONE), ValueCache.getFloat(1)));
      assertEquals(NumericConverter.BIG_DECIMAL, NumericConverter.resolveConverter(ValueCache.getBigDecimal(BigDecimal.ONE), ValueCache.getBigInteger(BigInteger.ONE)));
      assertEquals(NumericConverter.BIG_INTEGER, NumericConverter.resolveConverter(ValueCache.getBigInteger(BigInteger.ONE), ValueCache.getInteger(1)));
   }

}
