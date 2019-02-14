package org.ternlang.compile;

import junit.framework.TestCase;

public class NumberLiteralTest  extends TestCase {

   private static final String SOURCE=
   "println(0b01);\n"+
   "println(0xff);\n";

   public void testLiterals() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }

   public static void main(String[] list) throws Exception {
      new NumberLiteralTest().testLiterals();
   }
}
