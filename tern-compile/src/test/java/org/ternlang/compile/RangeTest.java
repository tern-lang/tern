package org.ternlang.compile;

public class RangeTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "let l = [0 to 9];\n"+
   "println(l);\n"+
   "assert l[0] == 0;\n"+
   "assert l[1] == 1;\n"+
   "assert l[2] == 2;\n"+
   "assert l[3] == 3;\n"+
   "assert l[4] == 4;\n"+
   "assert l[5] == 5;\n"+
   "assert l[6] == 6;\n"+
   "assert l[7] == 7;\n"+
   "assert l[8] == 8;\n"+
   "assert l[9] == 9;\n";

   private static final String SOURCE_2 =
   "let l = [0 from 9];\n"+
   "println(l);\n"+
   "assert l[0] == 9;\n"+
   "assert l[1] == 8;\n"+
   "assert l[2] == 7;\n"+
   "assert l[3] == 6;\n"+
   "assert l[4] == 5;\n"+
   "assert l[5] == 4;\n"+
   "assert l[6] == 3;\n"+
   "assert l[7] == 2;\n"+
   "assert l[8] == 1;\n"+
   "assert l[9] == 0;\n";

   public void testRanges() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
   }
}
