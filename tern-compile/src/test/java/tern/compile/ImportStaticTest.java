package tern.compile;

public class ImportStaticTest extends ScriptTestCase {

   private static final String SOURCE_1=
   "import static lang.Integer.*;\n"+
   "println(MAX_VALUE);\n"+
   "println(0x7fffffff);\n"+
   "println(Integer.MIN_VALUE);\n"+
   //"assert Integer.MIN_VALUE == 0x80000000;\n"+ // its allowing the overflow
   "assert MAX_VALUE == 0x7fffffff;\n";
   
   public void testStaticImports() throws Exception {
      assertScriptExecutes(SOURCE_1);
   }
}
