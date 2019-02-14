package org.ternlang.core.convert;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import junit.framework.TestCase;

import org.ternlang.core.MockType;
import org.ternlang.core.type.Type;
import org.ternlang.core.convert.FloatConverter;
import org.ternlang.core.convert.Score;

public class FloatConverterTest extends TestCase {
   
   public void testFloat() throws Exception {
      Type type = new MockType(null, null, null, Float.class);
      FloatConverter converter = new FloatConverter(type);
      
      assertEquals(converter.score(11.2f), Score.EXACT);
      assertEquals(converter.score(11.2d), Score.SIMILAR);
      assertEquals(converter.score(new BigDecimal("0.11")), Score.SIMILAR);
      assertEquals(converter.score(new AtomicLong(234L)), Score.COMPATIBLE);
      assertEquals(converter.score(new AtomicInteger(222)), Score.COMPATIBLE);
      assertEquals(converter.score(new Integer(33211)), Score.COMPATIBLE);
      assertEquals(converter.score("0.12"), Score.POSSIBLE);
      assertEquals(converter.score("-.012e+12"), Score.POSSIBLE);
      assertEquals(converter.score((Object)null), Score.POSSIBLE);
      
      assertEquals(converter.convert(11.2d), 11.2f);
      assertEquals(converter.convert(11.2d), 11.2f);
      assertEquals(converter.convert(new BigDecimal("0.11")), 0.11f);
      assertEquals(converter.convert(new AtomicLong(234L)), 234.0f);
      assertEquals(converter.convert(new AtomicInteger(222)), 222.0f);
      assertEquals(converter.convert(new Integer(33211)), 33211.0f);
      assertEquals(converter.convert("0.12"), 0.12f);
      assertEquals(converter.convert("-.012e+12"), -.012e+12f);
      assertEquals(converter.convert((Object)null), null);
   }
   
   public void testPrimitiveFloat() throws Exception {
      Type type = new MockType(null, null, null, float.class);
      FloatConverter converter = new FloatConverter(type);
      
      assertEquals(converter.score(11.2f), Score.EXACT);
      assertEquals(converter.score(new BigDecimal("0.11")), Score.SIMILAR);
      assertEquals(converter.score(new AtomicLong(234L)), Score.COMPATIBLE);
      assertEquals(converter.score("0.12"), Score.POSSIBLE);
      assertEquals(converter.score("-.012e+12"), Score.POSSIBLE);
      assertEquals(converter.score((Object)null), Score.INVALID);
      
      assertEquals(converter.convert(11.2d), 11.2f);
      assertEquals(converter.convert(11.2d), 11.2f);
      assertEquals(converter.convert(new BigDecimal("0.11")), 0.11f);
      assertEquals(converter.convert(new AtomicLong(234L)), 234.0f);
   }

}
