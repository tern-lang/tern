package org.ternlang.core.convert;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import junit.framework.TestCase;

import org.ternlang.core.MockType;
import org.ternlang.core.type.Type;
import org.ternlang.core.convert.Score;
import org.ternlang.core.convert.ShortConverter;

public class ShortConverterTest extends TestCase {

   public void testByte() throws Exception {
      Type type = new MockType(null, null, null, Short.class);
      ShortConverter converter = new ShortConverter(type);
      
      assertEquals(converter.score((short)11), Score.EXACT);
      assertEquals(converter.score(new BigDecimal("0.11")), Score.COMPATIBLE);
      assertEquals(converter.score(new AtomicLong(234L)), Score.SIMILAR);
      assertEquals(converter.score(new AtomicInteger(222)), Score.SIMILAR);
      assertEquals(converter.score(new Integer(33211)), Score.SIMILAR);
      assertEquals(converter.score("0.12"), Score.INVALID);
      assertEquals(converter.score("-.012e+12"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.POSSIBLE);
      
      assertEquals(converter.convert(11.2d), new Short((short)11));
      assertEquals(converter.convert(new BigDecimal("0.11")), new Short((short)0));
      assertEquals(converter.convert(new AtomicLong(234L)), new Short((short)234));
      assertEquals(converter.convert(new AtomicInteger(222)), new Short((short)222));
      assertEquals(converter.convert(new Integer(33211)), new Short((short)33211));
      assertEquals(converter.convert((Object)null), null);
   }
   
   public void testPrimitiveDouble() throws Exception {
      Type type = new MockType(null, null, null, short.class);
      ShortConverter converter = new ShortConverter(type);
      
      assertEquals(converter.score((short)11), Score.EXACT);
      assertEquals(converter.score(new BigDecimal("0.11")), Score.COMPATIBLE);
      assertEquals(converter.score(new AtomicLong(234L)), Score.SIMILAR);
      assertEquals(converter.score(new AtomicInteger(222)), Score.SIMILAR);
      assertEquals(converter.score(new Integer(33211)), Score.SIMILAR);
      assertEquals(converter.score((Object)null), Score.INVALID);
   }
}
