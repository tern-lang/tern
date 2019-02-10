package tern.parse;

import junit.framework.TestCase;

public class TokenReaderTest extends TestCase  {
   public void testX(){}
   
   public void testEscape() throws Exception {
      assertEquals(createReader("\"\\r\"").text(), "\r");
      assertEquals(createReader("\"\\r\\n\"").text(), "\r\n");      
   }
   
   public void testMixText() throws Exception {
      TextReader decoder = createReader("'some stuff='+x+\"\\nother stuff\"+'\\nyet more'+x");
      
      assertEquals(decoder.text(), "some stuff=");
      assertTrue(decoder.literal("+x+"));
      assertEquals(decoder.text(), "\nother stuff");
      assertTrue(decoder.literal("+"));
      assertEquals(decoder.text(), "\nyet more");
      assertTrue(decoder.literal("+x"));
      
   }
   public void testSingleQuoteText() throws Exception {      
      TextReader decoder = createReader("'hello world'\"middle 'blah' stuff\"'stuff''a\\'b\\'''something\nover\nmany\nlines\\'blah\\''");
      
      assertEquals(decoder.text(), "hello world");
      assertEquals(decoder.text(), "middle 'blah' stuff");
      assertEquals(decoder.text(), "stuff");
      assertEquals(decoder.text(), "a'b'");      
      assertEquals(decoder.text(), "something\nover\nmany\nlines'blah'");        

   }

   public void testQualifier() throws Exception {      
      TextReader decoder = createReader("java.lang.*;java.lang.Math.*;java.lang.Math.round");
      
      assertEquals(decoder.qualifier(), "java.lang");
      assertTrue(decoder.literal(".*;"));
      assertEquals(decoder.qualifier(), "java.lang.Math");
      assertTrue(decoder.literal(".*;"));
      assertEquals(decoder.qualifier(), "java.lang.Math.round");        

   }

   public void testComments() throws Exception {
      TextReader decoder = createReader("0xff;hello,12.0f/2.0f,\"some string with stuff\\\"blah\\\"d\"");
      
      assertEquals(decoder.hexidecimal(), 255);
      assertEquals(decoder.next(),';');      
      assertEquals(decoder.identifier(), "hello");
      assertEquals(decoder.next(),',');  
      assertEquals(decoder.decimal(), 12.0f);
      assertEquals(decoder.next(), '/');
      assertEquals(decoder.decimal(), 2.0f);
      assertEquals(decoder.next(), ',');
      assertEquals(decoder.text(),"some string with stuff\"blah\"d"); // not escaped!!!! // some string with stuff \"blah\"d
   }
   
   public void testHex() throws Exception {
      TextReader decoder = createReader("0xff");

      assertEquals(decoder.hexidecimal(), 255);              
 
   }
   
   public void testNumbers() throws Exception {
      TextReader decoder1 = createReader("1L,20L,11,12.002");
      
      assertEquals(decoder1.decimal(), 1L);    
      assertEquals(decoder1.next(), (','));
      assertEquals(decoder1.decimal(), 20L);    
      assertEquals(decoder1.next(), (','));
      assertEquals(decoder1.decimal(), 11);    
      assertEquals(decoder1.next(), (','));
      assertEquals(decoder1.decimal(), 12.002);  
      
      TextReader decoder2 = createReader("1,20,11,12.002,11d,124.0f");
      
      assertEquals(decoder2.decimal(), 1);    
      assertEquals(decoder2.next(), (','));
      assertEquals(decoder2.decimal(), 20);    
      assertEquals(decoder2.next(), (','));
      assertEquals(decoder2.decimal(), 11);    
      assertEquals(decoder2.next(), (','));
      assertEquals(decoder2.decimal(), 12.002d);
      assertEquals(decoder2.next(), (','));
      assertEquals(decoder2.decimal(), 11d);
      assertEquals(decoder2.next(), (','));
      assertEquals(decoder2.decimal(), 124.0f);       
 
   }
   
   public void testDecoder() throws Exception {
      TextReader decoder = createReader("this,is,a,\"simple\",test,12");
      
      assertEquals(decoder.identifier(), "this");
      assertEquals(decoder.peek(), (','));
      assertEquals(decoder.next(), (','));
      assertEquals(decoder.identifier(), "is");
      assertEquals(decoder.next(), (','));
      assertEquals(decoder.identifier(), "a");
      assertEquals(decoder.next(),(','));
      assertEquals(decoder.text(), "simple");
      assertEquals(decoder.next(), (','));
      assertEquals(decoder.identifier(), "test");
      assertEquals(decoder.next(), (','));
      assertEquals(decoder.decimal().intValue(), 12);        
   }

   public void testEscapeDecoder() throws Exception {
      TextReader decoder = createReader("\"a \\u0063\\u006c\\u0061\\u0073\\u0073\\u0020\\u0054\\u0065\\u0073\\u0074 714 \"");

      System.err.println(decoder.text());
   }
   
   private TextReader createReader(String text) {
      SourceProcessor processor = new SourceProcessor(Short.MAX_VALUE);
      SourceCode code = processor.process(text);
      return new TextReader(code.getSource(), code.getTypes());
   }
}
