package org.ternlang.compile;

public class ForInLoopTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "for(let i in 0..9){\n"+
   "   assert i > -1;\n"+
   "}\n"+
   "let l = [];\n"+
   "for(i in 0 .. 9){\n"+
   "   l.add(i);\n"+
   "   assert i > -1;\n"+
   "}\n"+
   "println(l);\n"+
   "assert l.length == 10;\n"+
   "assert l[2] == 2;\n"+
   "assert l[3] == 3;\n"+
   "assert l[9] == 9;\n";

   private static final String SOURCE_2 =
   "for(let i in 0 to 9){\n"+
    "   assert i > -1;\n"+
    "}\n"+
    "let l = [];\n"+
    "for(var i in 0 to 9){\n"+
    "   l.add(i);\n"+
    "   assert i > -1;\n"+
    "}\n"+
    "println(l);\n"+
    "assert l.length == 10;\n"+
    "assert l[2] == 2;\n"+
    "assert l[3] == 3;\n"+
    "assert l[9] == 9;\n";

   private static final String SOURCE_3 =
   "for(let i in 0 from 9){\n"+
   "   assert i > -1;\n"+
   "}\n"+
   "let l = [];\n"+
   "for(var i in 0 from 9){\n"+
   "   l.add(i);\n"+
   "   assert i > -1;\n"+
   "}\n"+
   "println(l);\n"+
   "assert l.length == 10;\n"+
   "assert l[0] == 9;\n"+
   "assert l[9] == 0;\n";

   public void testForInLoop() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
   }
}
