package org.ternlang.compile;

public class GlobalVarTest extends ScriptTestCase {

   private static final String SOURCE_1=
   "let GLOBAL = 11;\n"+
   "function fun(n) {\n"+
   "   GLOBAL++;\n"+
   "}\n"+
   "fun(30);\n";

   private static final String SOURCE_2=
   "const GLOBAL = 11;\n"+
   "function fun(n) {\n"+
   "   println(GLOBAL);\n"+
   "   println(\"global=${GLOBAL}\");\n"+
   "   assert \"global=${GLOBAL}\" == 'global=11';\n"+
   "   return GLOBAL + n;\n"+
   "}\n"+
   "var result = fun(30);\n"+
   "println(result);\n";
   
   private static final String SOURCE_3=
   "const GLOBAL = 11;\n"+
   "class X{\n"+
   "  ff(n) {\n"+
   "     return GLOBAL + n;\n"+
   "  }\n"+
   "}\n"+
   "var x = new X();\n"+
   "var result = x.ff(30);\n"+
   "println(result);\n";

   public void testGlobalVar() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
   }
   
//   public void testGlobalVarInClass() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      Executable executable = compiler.compile(SOURCE_2);
//      executable.execute();
//   }

   public static void main(String[] list) throws Exception {
      new GlobalVarTest().testGlobalVar();
//      new GlobalVarTest().testGlobalVarInClass();
   }
}
