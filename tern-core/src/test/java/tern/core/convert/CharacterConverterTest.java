package tern.core.convert;

import junit.framework.TestCase;

import tern.core.MockType;
import tern.core.type.Type;
import tern.core.convert.CharacterConverter;
import tern.core.convert.Score;

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
