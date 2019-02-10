package tern.compile;

import junit.framework.TestCase;

public class ClassCallModuleFunctionTest extends TestCase {
   
   private static final String SOURCE = 
   "class Foo {\n"+
   "   call(){\n"+
   "      fun();\n"+
   "   }\n"+
   "   fun(x){\n"+
   "      println('error');\n"+
   "   }\n"+
   "}\n"+
   "function fun(v...){\n"+
   "   println('x');\n"+
   "}\n"+
   "new Foo().call();\n";

   public void testModule() throws Exception {
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
