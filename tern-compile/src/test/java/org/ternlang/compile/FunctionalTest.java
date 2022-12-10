package org.ternlang.compile;

import junit.framework.TestCase;

public class FunctionalTest extends TestCase {

   private static final String SOURCE_1 =
   "let o = Some(11);\n"+
   "assert o.isDefined();\n"+
   "assert !o.isEmpty();\n"+
   "assert o.get() == 11;\n";

   private static final String SOURCE_2 =
   "let o = None();\n"+
   "assert o.isEmpty();\n"+
   "assert !o.isDefined();\n";

   public void testSome() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      Timer.timeExecution("testSome", executable);
   }

   public void testNone() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      Timer.timeExecution("testNone", executable);
   }
}
