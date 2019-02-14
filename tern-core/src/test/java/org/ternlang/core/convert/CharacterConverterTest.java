package org.ternlang.core.convert;

import junit.framework.TestCase;

import org.ternlang.core.MockType;
import org.ternlang.core.type.Type;
import org.ternlang.core.convert.CharacterConverter;
import org.ternlang.core.convert.Score;

public class CharacterConverterTest extends TestCase {

   public void testCharacter() throws Exception {
      Type type = new MockType(null, null, null, Character.class);
      CharacterConverter converter = new CharacterConverter(type);
      
      assertEquals(converter.score('s'), Score.EXACT);
      assertEquals(converter.score("s"), Score.POSSIBLE);
      assertEquals(converter.score("ss"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.POSSIBLE);
      
      assertEquals(converter.convert('s'), 's');
      assertEquals(converter.convert("s"), 's');
      assertEquals(converter.convert((Object)null), null);
   }
   
   public void testPrimitiveCharacter() throws Exception {
      Type type = new MockType(null, null, null, char.class);
      CharacterConverter converter = new CharacterConverter(type);
      
      assertEquals(converter.score('s'), Score.EXACT);
      assertEquals(converter.score("s"), Score.POSSIBLE);
      assertEquals(converter.score("ss"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.INVALID);
      
      assertEquals(converter.convert('s'), 's');
      assertEquals(converter.convert("s"), 's');
   }
}
