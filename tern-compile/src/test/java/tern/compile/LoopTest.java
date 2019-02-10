package tern.compile;

public class LoopTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "let l = [];\n"+
   "let i = 0;\n"+
   "until(i == 10) {\n"+
   "   l.add(i++);\n"+
   "}\n"+
   "assert l.length == 10;\n"+
   "assert l[0] == 0;\n"+
   "assert l[9] == 9;\n";

   private static final String SOURCE_2 =
   "let l = [];\n"+
   "let i = 0;\n"+
   "while(i != 10) {\n"+
   "   l.add(i++);\n"+
   "}\n"+
   "assert l.length == 10;\n"+
   "assert l[0] == 0;\n"+
   "assert l[9] == 9;\n";

   public void testLoops() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
   }
}
