package org.ternlang.compile;

public class DynamicVariableReferenceTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "let entry = [];\n"+
   "entry.list[entry.pos++];\n";

   public void testReference() throws Exception {
      assertScriptCompiles(SOURCE_1);
   }
}
