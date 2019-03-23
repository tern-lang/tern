package org.ternlang.compile;

public class StringConcatTest extends ScriptTestCase {

   private static final String SOURCE =
   "import swing.JFrame;\n"+
   "class Foo{\n"+
   "   const frame;\n"+
   "   new() {\n"+
   "      this.frame = new JFrame(\"Simple Physics Cannon Test - \" + new Date());\n"+
   "   }\n"+
   "}\n"+
   "new Foo();\n";

   public void testConcat() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
