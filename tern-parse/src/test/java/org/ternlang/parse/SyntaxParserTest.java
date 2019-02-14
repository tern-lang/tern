package org.ternlang.parse;

import junit.framework.TestCase;

public class SyntaxParserTest extends TestCase {
   
   private static final String GRAMMAR_FILE = "grammar.txt";
   
   private static final String SOURCE_1 =
   "/* simple test class */\r\n"+
   "class Blah {\r\n"+
   "  var x;\r\n"+
   "  var y;\r\n"+
   "\r\n"+
   "  new(x,y){\r\n"+
   "    this.x=x;\r\n"+
   "    this.y=y;\r\n"+
   "  }\r\n"+
   "  test(){\r\n" +
   "    return x+y;\r\n"+
   "  }\r\n"+
   "}\r\n";

   private static final String SOURCE_2 =
   "trait Scene {}\n"+
   "class Sphere with Scene {}\n";
   
   private static final String SOURCE_3 = 
   "class Operation {\n"+
   "   operate(){\n"+
   "     return 'Operation.operate()'\n"+ 
   "   }\n"+
   "}\n";
   
   public void testSimpleParser() throws Exception {
      System.err.println(SOURCE_1);
      SyntaxCompiler builder = new SyntaxCompiler(GRAMMAR_FILE);
      SyntaxParser parser = builder.compile();
      LexerBuilder.print(parser, SOURCE_1, "script");
   }

   public void testComplexParser() throws Exception {
      System.err.println(SOURCE_2);
      SyntaxCompiler builder = new SyntaxCompiler(GRAMMAR_FILE);
      SyntaxParser parser = builder.compile();
      LexerBuilder.print(parser, SOURCE_2, "script");
   }
   
   public void testSyntaxErrorParser() throws Exception {
      boolean failure = false;
      try {
         System.err.println(SOURCE_3);
         SyntaxCompiler builder = new SyntaxCompiler(GRAMMAR_FILE);
         SyntaxParser parser = builder.compile();
         LexerBuilder.print(parser, SOURCE_3, "script");
      }catch(Exception e) {
         assertEquals(e.getMessage(), "Syntax error at line 4");
         failure = true;
         e.printStackTrace();
      }
      assertTrue(failure);
   }
}
