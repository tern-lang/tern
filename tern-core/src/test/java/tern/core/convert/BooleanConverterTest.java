package tern.core.convert;

import junit.framework.TestCase;

import tern.core.MockType;
import tern.core.type.Type;
import tern.core.convert.BooleanConverter;
import tern.core.convert.Score;

public class BooleanConverterTest extends TestCase {

   public void testBoolean() throws Exception {
      Type type = new MockType(null, null, null, Boolean.class);
      BooleanConverter converter = new BooleanConverter(type);
      
      assertEquals(converter.score(true), Score.EXACT);
      assertEquals(converter.score(Boolean.TRUE), Score.EXACT);
      assertEquals(converter.score(Boolean.FALSE), Score.EXACT);
      assertEquals(converter.score("true"), Score.POSSIBLE);
      assertEquals(converter.score("false"), Score.POSSIBLE);
      assertEquals(converter.score("TRUE"), Score.POSSIBLE);
      assertEquals(converter.score("FALSE"), Score.POSSIBLE);
      assertEquals(converter.score("yes"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.POSSIBLE);
      
      assertEquals(converter.convert(true), Boolean.TRUE);
      assertEquals(converter.convert(false), Boolean.FALSE);
      assertEquals(converter.convert("true"), Boolean.TRUE);
      assertEquals(converter.convert("false"), Boolean.FALSE);
      assertEquals(converter.convert("TRUE"), Boolean.TRUE);
      assertEquals(converter.convert("FALSE"), Boolean.FALSE);
      assertEquals(converter.convert((Object)null), null);
   }
   
   public void testPrimitiveBoolean() throws Exception {
      Type type = new MockType(null, null, null, boolean.class);
      BooleanConverter converter = new BooleanConverter(type);
      
      assertEquals(converter.score(true), Score.EXACT);
      assertEquals(converter.score(Boolean.TRUE), Score.EXACT);
      assertEquals(converter.score(Boolean.FALSE), Score.EXACT);
      assertEquals(converter.score("true"), Score.POSSIBLE);
      assertEquals(converter.score("false"), Score.POSSIBLE);
      assertEquals(converter.score("TRUE"), Score.POSSIBLE);
      assertEquals(converter.score("FALSE"), Score.POSSIBLE);
      assertEquals(converter.score("yes"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.INVALID);
   }
}
