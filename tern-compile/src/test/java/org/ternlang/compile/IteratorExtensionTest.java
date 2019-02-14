package org.ternlang.compile;

public class IteratorExtensionTest extends ScriptTestCase {
   
   private static final String SOURCE =
   "let x = [1,2,3,4,5,6,7,8];\n"+
   "let y = [];\n"+
   "x.iterator().filter(e -> e % 2 == 0).reverse().forEachRemaining(e -> y.add(e));\n"+
   "assert y[0] == 8;\n"+
   "assert y[1] == 6;\n"+
   "assert y[2] == 4;\n"+
   "assert y[3] == 2;\n"+
   "assert y.length == 4;\n";

   public void testIteratorExtension() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
