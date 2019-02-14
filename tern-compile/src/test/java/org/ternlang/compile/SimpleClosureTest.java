package org.ternlang.compile;

import junit.framework.TestCase;

public class SimpleClosureTest extends TestCase {

   private static final String SOURCE =
   "var x = 2;\n"+
   "var f = ->println(x);\n"+
   "f();\n";
   
   public void testFunctionScope() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
