package tern.compile;

public class ScopeInheritanceTest extends ScriptTestCase {

   private static final String SOURCE_1 =
    "for(i in 0..2){\n"+
    "   fun(i);\n"+
    "}\n"+
    "\n"+
    "func fun(i){\n"+
    "   println(i);\n"+
    "}\n";

   private static final String SOURCE_2 =
    "for(i in 0..2){\n"+
    "   fun(`value-${i}`);\n"+
    "}\n"+
    "\n"+
    "func fun(i){\n"+
    "   assert `${i.class}` == 'lang.String';\n"+
    "   assert i.startsWith('value-');\n" +
    "   println(`${i.class}`);\n"+
    "   println(i.class);\n"+
    "   println(i.substring(1));\n"+
    "}\n";

   private static final String SOURCE_3 =
    "let a=1;\n"+
    "func f(a){\n"+
    "   assert a == `aaa1`;\n"+
    "}\n"+
    "f('aaa'+a);\n";


   public void testInheritance() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
   }

}
