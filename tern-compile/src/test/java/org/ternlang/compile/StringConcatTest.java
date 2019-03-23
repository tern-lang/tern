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

   public void testConcat() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
   }
}
