package org.ternlang.compile;

public class FunctionAsClosureTest extends ScriptTestCase {

   private static final String SOURCE =
   "class Foo{\n"+
   "   test(n){\n"+
   "      if(n >0) {\n"+
   "         for(fn in this.class.functions){\n"+
   "            if(fn.name.equals('test')){\n"+
   "               fn(n-1);\n"+
   "            }\n"+
   "         }\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "Foo().test(10);\n";

   public void testScript() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
