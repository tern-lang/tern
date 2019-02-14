package org.ternlang.parse;

import junit.framework.TestCase;

public class SourceCompressorTest extends TestCase {

   private static final String SOURCE=
   "/**\n"+
   " * Point class.\n"+
   " */\n"+
   "class Point {\n"+
   "   // variables\n"+
   "   var x;\n"+
   "   var y;\n"+
   "\n"+
   "   new(x,y){\n"+
   "      this.x = x;\n"+
   "      this.y = y;\n"+
   "   }\n"+
   "\n"+
   "   transform(x,y){\n"+
   "      return new Point(x+this.x, y+this.y); /* simple transform */\n"+
   "   }\n"+
   "}\n";
   
   public void testSimpleAnnotationCompress() throws Exception {
      char[] text = "@Blah @Blah blah(){}".toCharArray();
      SourceCompressor compressor = new SourceCompressor(text);
      SourceCode code = compressor.compress();
      char[] original = code.getOriginal();
      char[] compress = code.getSource();
      short[] lines = code.getLines();
      System.err.println(">"+new String(original)+"<");
      System.err.println(">"+new String(compress)+"<");
   }
   
   public void testAnnotationCompress() throws Exception {
      char[] text = "@Blah(x:1) @Blah blah(){}".toCharArray();
      SourceCompressor compressor = new SourceCompressor(text);
      SourceCode code = compressor.compress();
      char[] original = code.getOriginal();
      char[] compress = code.getSource();
      short[] lines = code.getLines();
      System.err.println(">"+new String(original)+"<");
      System.err.println(">"+new String(compress)+"<");
   }

   public void testVariableCompress() throws Exception {
      char[] text = "x".toCharArray();
      SourceCompressor compressor = new SourceCompressor(text);
      SourceCode code = compressor.compress();
      char[] original = code.getOriginal();
      char[] compress = code.getSource();
      short[] lines = code.getLines();
      System.err.println(">"+new String(original)+"<");
      System.err.println(">"+new String(compress)+"<");
   }
   
   public void testSourceCompress() throws Exception {
      char[] text = SOURCE.toCharArray();
      SourceCompressor compressor = new SourceCompressor(text);
      SourceCode code = compressor.compress();
      char[] original = code.getOriginal();
      char[] compress = code.getSource();
      short[] lines = code.getLines();
      System.err.println(">"+new String(original)+"<");
      System.err.println(">"+new String(compress)+"<");
   }
}
