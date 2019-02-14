package org.ternlang.compile;


public class ImportResourceTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "import util.regex.Pattern;\n"+
   "class Foo {\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Foo(${x},${y})\";\n"+
   "   }\n"+
   "}\n"+
   "class Bar{\n"+
   "   var text;\n"+
   "   new(text){\n"+
   "      this.text=text;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Bar(${text})\";\n"+
   "   }\n"+
   "}\n";
         
   private static final String SOURCE_2 =
   "import example.Foo;\n"+
   "import example.Bar;\n"+
   "\n"+
   "class Demo{\n"+
   "   var foo;\n"+
   "   new(x,y){\n"+
   "      this.foo = new Foo(x,y);\n"+
   "   }\n"+
   "   get(): Foo{\n"+
   "      return foo;\n"+
   "   }\n"+
   "}";

   private static final String SOURCE_3 =
   "import demo.Demo;\n"+
   "\n"+
   "class Builder{\n"+
   "   var demo;\n"+
   "   new(text){\n"+
   "      this.demo = new Demo(text,1);\n"+
   "   }\n"+
   "   get(): Demo{\n"+
   "      return demo;\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_4 =
   "import example.Bar;\n"+
   "import demo.Demo;\n"+   
   "\n"+
   "var bar = new Bar(\"test\");\n"+
   "var demo = new Demo(\"x\",33);\n"+
   "var foo = demo.get();\n"+
   "\n"+
   "println(bar);\n"+
   "println(foo);\n";     
   
   private static final String SOURCE_5 =
   "import builder.Builder;\n"+
   "\n"+
   "var example = new Builder(\"test\");\n"+
   "var demo = example.get();\n"+
   "\n"+
   "println(demo);\n";   
         
   public void testImports() throws Exception {
      addScript("/example.tern", SOURCE_1);
      addScript("/demo.tern", SOURCE_2);
      addScript("/builder/Builder.tern", SOURCE_3);
      addScript("/main.tern", SOURCE_4);
      addScript("/launch.tern", SOURCE_5);
      assertScriptExecutes("/main.tern");
      assertScriptExecutes("/launch.tern");
   }
}
