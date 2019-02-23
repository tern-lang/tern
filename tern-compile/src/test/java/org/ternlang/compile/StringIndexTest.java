package org.ternlang.compile;

public class StringIndexTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "let x = \"abcdefg\";\n"+
   "let y = x[2];\n"+
   "\n"+
   "assert y == 'c';\n"+
   "assert y.class == Character.class;";

   public void testStringIndex() throws Exception {
      assertScriptExecutes(SOURCE_1);
   }
}
