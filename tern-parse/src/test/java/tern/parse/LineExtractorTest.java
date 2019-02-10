package tern.parse;

import junit.framework.TestCase;

public class LineExtractorTest extends TestCase {
   
   private static final String SOURCE=
   "/**\n"+//1
   " * Point class.\n"+//2
   " */\n"+//3
   "class Point {\n"+//4
   "   // variables\n"+//5
   "   var x;\n"+//6
   "   var y;\n"+//7
   "\n"+//8
   "   new(x,y){\n"+//9
   "      this.x = x;\n"+//10
   "      this.y = y;\n"+//11
   "   }\n"+//12
   "\n"+//13
   "   transform(x,y){\n"+//14
   "      return new Point(x+this.x, y+this.y); /* simple transform */\n"+//15
   "   }\n"+//16
   "}";//17
   
   public void testExtractor() {
      LineExtractor extractor = new LineExtractor("test", SOURCE.toCharArray(), 17);
      
      assertEquals(extractor.extract(1).getNumber(), 1);
      assertEquals(extractor.extract(1).getSource(), "/**");
      assertEquals(extractor.extract(4).getNumber(), 4);
      assertEquals(extractor.extract(4).getSource(), "class Point {");
      assertEquals(extractor.extract(17).getNumber(), 17);
      assertEquals(extractor.extract(17).getSource(), "}");
   }

}
