package org.ternlang.compile;

import junit.framework.TestCase;

public class SimpleFunctionTest extends TestCase {

   private static final String SOURCE_1 = 
   "function foo(x){\n"+
   "  return x++;\n"+
   "}\n"+
   "foo(1);\n";

   public void testSimpleFunction() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   
   }
}
