package org.ternlang.parse;

import junit.framework.TestCase;

public class QuoteParseTest extends TestCase {
   
   public void testEscapeSlash() throws Exception {
      String text=new SourceCompressor("'\\\\'".toCharArray()).compress().toString(); 
      System.err.println(text);
   }
   
   public void testOpenQuote() throws Exception {
      boolean failure=false;
      try{
         String text=new SourceCompressor("var x=\"\";\r\nfor(var i = 0; i < 10; i++){\r\nx+=\";\r\n}".toCharArray()).compress().toString(); 
         System.err.println(text);
      } catch(Exception e){
         failure=true;
         e.printStackTrace();
      }
      assertTrue("Quote not closed properly is an error", failure);
   }
   
   public void testParse() throws Exception {
      assertEquals(new SourceCompressor("\"c:\\\\\"".toCharArray()).compress().toString(), "\"c:\\\\\""); // "c:\\"
      assertEquals(new SourceCompressor(new char[]{'"', 'c', ':', '\\', '\\', '\\', '"', '"'}).compress().toString(), new String(new char[]{'"', 'c', ':', '\\', '\\', '\\', '"', '"'})); // "c:\\\""
      assertEquals(new SourceCompressor(new char[]{'"', '"'}).compress().toString(), new String(new char[]{'"', '"'})); // ""
      assertEquals(new SourceCompressor(new char[]{'\'', '\''}).compress().toString(), new String(new char[]{'\'', '\''})); // ''
      boolean failure=false;
      try{
         new SourceCompressor("/** // */".toCharArray()).compress().toString(); // /** // */
      } catch(Exception e){
         failure=true;
         e.printStackTrace();
      }
      assertTrue("Empty source should be an error", failure);
      
      failure=false;
      try{
         new SourceCompressor("/// this is a comment".toCharArray()).compress().toString(); // /** // */
      } catch(Exception e){
         failure=true;
         e.printStackTrace();
      }
      assertTrue("Empty source should be an error", failure);
      
      failure=false;
      try{
         new SourceCompressor("/* abc */ // another one".toCharArray()).compress().toString(); // /** // */
      } catch(Exception e){
         failure=true;
         e.printStackTrace();
      }
      assertTrue("Empty source should be an error", failure);
      
      failure=false;
      try{
         new SourceCompressor("/// this is a comment\n".toCharArray()).compress().toString(); // /** // */
      } catch(Exception e){
         failure=true;
         e.printStackTrace();
      }
      assertTrue("Empty source should be an error", failure);
      
      failure=false;
      try{
         new SourceCompressor("/// this is a comment\n\n".toCharArray()).compress().toString(); // /** // */
      } catch(Exception e){
         failure=true;
         e.printStackTrace();
      }
      assertTrue("Empty source should be an error", failure);
   }

   public void testCompress() throws Exception {
      assertEquals(new SourceCompressor("'some stuff='+x + \"\\nother stuff\" + '\\nyet more'+x".toCharArray()).compress().toString(), 
            "'some stuff='+x+\"\\nother stuff\"+'\\nyet more'+x");
      assertEquals(new SourceCompressor("'some stuff='+x+\"\\nother stuff\"+'\\nyet more'+x".toCharArray()).compress().toString(), 
            "'some stuff='+x+\"\\nother stuff\"+'\\nyet more'+x");
   }
}
