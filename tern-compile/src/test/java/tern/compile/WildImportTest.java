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
      addScript("/test.tern", SOURCE_1);
      addScript("/test/Bar.tern", SOURCE_2);
      addScript("/foo/Foo.tern", SOURCE_3);
      addScript("/blah/Blah.tern", SOURCE_4);
      assertScriptExecutes("/test.tern");
   }
}
