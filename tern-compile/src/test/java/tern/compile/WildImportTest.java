package tern.compile;


public class WildImportTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "import foo.*;\n"+
   "import test.*;\n"+
   "import blah.*;\n"+
   "\n"+
   "println(new Bar(1,2));\n";
         
   private static final String SOURCE_2 =
   "class Bar{\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   get(){\n"+
   "      \"Bar(${x},${y})\";\n"+
   "   }\n"+
   "}";
   
   private static final String SOURCE_3 =
   "module Foo{\n"+
   "   dump(){\n"+
   "      \"Foo.dump()\";\n"+
   "   }\n"+
   "}";
   
   private static final String SOURCE_4 =
   "class Blah{\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   get(){\n"+
   "      \"Blah(${x},${y})\";\n"+
   "   }\n"+
   "}";
         
   public void testImports() throws Exception {
      addScript("/test.snap", SOURCE_1);
      addScript("/test/Bar.snap", SOURCE_2);   
      addScript("/foo/Foo.snap", SOURCE_3);  
      addScript("/blah/Blah.snap", SOURCE_4);  
      assertScriptExecutes("/test.snap"); 
   }
}
