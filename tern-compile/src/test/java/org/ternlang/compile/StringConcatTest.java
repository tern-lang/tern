package org.ternlang.compile;

public class StringConcatTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "import swing.JFrame;\n"+
   "class Foo{\n"+
   "   const frame;\n"+
   "   new() {\n"+
   "      this.frame = new JFrame(\"Simple Physics Cannon Test - \" + new Date());\n"+
   "   }\n"+
   "}\n"+
   "new Foo();\n";

   private static final String SOURCE_2 =
   "let a = 10, b = 90;\n"+
   "assert Math.sqrt(a + b) == 10;\n";

   private static final String SOURCE_3 =
   "let a = 10, b = 90;\n"+
   "assert Math.cos(a + Math.PI) != null;\n";

   private static final String SOURCE_4 =
   "class Blah{\n"+
   "   class Bar{\n"+
   "      const f;\n"+
   "      new(f){\n"+
   "         this.f=f;\n"+
   "      }\n"+
   "   }\n"+
   "   const x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"${x},${y}\";\n"+
   "   }\n"+
   "}\n"+
   "println(new Blah.Bar(\"X\")+\": \"+Thread.currentThread());";

   public void testConcat() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
      assertScriptExecutes(SOURCE_4);
   }
}
