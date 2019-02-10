package tern.compile;

public class IfAndUnlessTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "let i = 1;\n"+
   "let l = [];\n"+
   "\n"+
   "if(i == 1){\n"+
   "   l.add('if(i == 1)');\n"+
   "}\n"+
   "if(i == 2) {\n"+
   "   l.add('if(i == 2)');\n"+
   "}\n"+
   "unless(i == 1) {\n"+
   "   l.add('unless(i == 1)');\n"+
   "}\n"+
   "unless(i == 2) {\n"+
   "   l.add('unless(i == 2)');\n"+
   "}\n"+
   "println(l);\n"+
   "assert l[0] == 'if(i == 1)';\n"+
   "assert l[1] == 'unless(i == 2)';\n";


   public void testIfAndUnless() throws Exception {
      assertScriptExecutes(SOURCE_1);
   }
}
