package org.ternlang.parse;

import junit.framework.TestCase;

public class TextDecoderTest extends TestCase {

   public void testDecoder() throws Exception{
      TextDecoder decoder = new TextDecoder("\\r".toCharArray());
      String text = decoder.decode(0, "\\r".length());
      assertEquals(text, "\r");
      decoder = new TextDecoder("\\r\\n\\b\\f\\r".toCharArray());
      text = decoder.decode(0, "\\r\\n\\b\\f\\r".length());
      assertEquals(text, "\r\n\b\f\r");
   }
}
