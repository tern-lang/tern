package org.ternlang.core.convert;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import junit.framework.TestCase;

import org.ternlang.core.MockType;
import org.ternlang.core.type.Type;
import org.ternlang.core.convert.DoubleConverter;
import org.ternlang.core.convert.Score;

public class DoubleConverterTest extends TestCase {
   
   public void testDouble() throws Exception {
      Type type = new MockType(null, null, null, Double.class);
      DoubleConverter converter = new DoubleConverter(type);
      
      assertEquals(converter.score(11.2d), Score.EXACT);
      assertEquals(converter.score(new BigDecimal("0.11")), Score.SIMILAR);
      assertEquals(converter.score(new AtomicLong(234L)), Score.COMPATIBLE);
      assertEquals(converter.score(new AtomicInteger(222)), Score.COMPATIBLE);
      assertEquals(converter.score(new Integer(33211)), Score.COMPATIBLE);
      assertEquals(converter.score("0.12"), Score.INVALID);
      assertEquals(converter.score("-.012e+12"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.POSSIBLE);
      
      assertEquals(converter.convert(11.2d), 11.2d);
      assertEquals(converter.convert(new BigDecimal("0.11")), 0.11d);
      assertEquals(converter.convert(new AtomicLong(234L)), 234.0d);
      assertEquals(converter.convert(new AtomicInteger(222)), 222.0d);
      assertEquals(converter.convert(new Integer(33211)), 33211.0d);
      assertEquals(converter.convert((Object)null), null);
   }
   
   public void testPrimitiveDouble() throws Exception {
      Type type = new MockType(null, null, null, double.class);
      DoubleConverter converter = new DoubleConverter(type);
      
      assertEquals(converter.score(11.2d), Score.EXACT);
      assertEquals(converter.score(new BigDecimal("0.11")), Score.SIMILAR);
      assertEquals(converter.score(new AtomicLong(234L)), Score.COMPATIBLE);
      assertEquals(converter.score("0.12"), Score.INVALID);
      assertEquals(converter.score("-.012e+12"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.INVALID);
   }

}
