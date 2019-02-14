package org.ternlang.compile;

public class ImportListTest extends ScriptTestCase {

   private static final String SOURCE_1=
   "import lang.Long;\n"+
   "import lang.{Double, Integer, Short};\n"+
   "println(Integer.MAX_VALUE);\n";

   public void testImportList() throws Exception {
      assertScriptExecutes(SOURCE_1);
   }
}
