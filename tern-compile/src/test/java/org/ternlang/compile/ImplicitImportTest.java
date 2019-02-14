package org.ternlang.compile;

public class ImplicitImportTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "class Nuh{\n"+
   "   const x, y;\n"+
   "   new(x, y){\n"+
   "      println(x);\n"+
   "      println(y);\n"+   
   "      this.x =x;\n"+
   "      this.y = y;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return `x=${x} y=${y}`;\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_2 =
   "class Foo{\n"+
   "   const x: Nuh;\n"+
   "   new(x){\n"+
   "      println(x);\n"+
   "      this.x = new Nuh(x, 1);\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return `nuh=(${x})`;\n"+
   "   }\n"+
   "}\n";
         
   private static final String SOURCE_3 =
   "class Blah {\n"+
   "   func(y): Foo{\n"+
   "      return new Foo(y);\n"+
   "   }\n"+
   "}\n";

   private static final String SOURCE_4 =
   "println(new Blah().func(112).toString());\n"+      
   "assert new Blah().func(112).toString() == 'nuh=(x=112 y=1)';\n";  
         
   private static final String SOURCE_5 =
   "import example.Blah;\n"+ 
   "println(new Blah().func(112).toString());\n"+   
   "assert new Blah().func(112).toString() == 'nuh=(x=112 y=1)';\n";  
   
   public void testImplicitImportFromSameModule() throws Exception {
      addScript("/example/Nuh.tern", SOURCE_1);
      addScript("/example/Foo.tern", SOURCE_2);
      addScript("/example/Blah.tern", SOURCE_3);
      addScript("/example.tern", SOURCE_4);
      assertScriptExecutes("/example.tern");
   }
   
   public void testImplicitImports() throws Exception {
      addScript("/example/Nuh.tern", SOURCE_1);
      addScript("/example/Foo.tern", SOURCE_2);
      addScript("/example/Blah.tern", SOURCE_3);
      addScript("/main.tern", SOURCE_5);
      assertScriptExecutes("/main.tern");
   }
}
