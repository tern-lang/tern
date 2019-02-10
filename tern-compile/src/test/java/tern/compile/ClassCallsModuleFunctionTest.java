package tern.compile;

import junit.framework.TestCase;

public class ClassCallsModuleFunctionTest extends TestCase {

   private static final String SOURCE =
   "import static lang.Math.*;\n"+
   "class Foo {\n"+
   "   call(x){\n"+
   "      fun(x, max(10, 5));\n"+
   "   }\n"+
   "}\n"+
   "function fun(a, b){\n"+
   "   return a+b;\n"+
   "}\n"+
   "assert new Foo().call(1) == 11;\n"+
   "assert new Foo().call(4) == 14;\n";

   public void testClassCallsFunction() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
