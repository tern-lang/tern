package org.ternlang.compile;

import junit.framework.TestCase;

public class StringExtensionTest extends TestCase {

   private static final String SOURCE_1 =
   "\"hello\".zip().forEach(e -> {\n"+
   "   println(e.index + \"=\" + e.value);\n"+
   "   assert e.value == e.source.charAt(e.index);\n"+
   "});\n";

   private static final String SOURCE_2 =
   "assert \"11\".toInteger() == 11;\n"+
   "assert \"21\".toLong() == 21L;\n";

   private static final String SOURCE_3 =
   "assert \"a\".get(0).plus(2) == 99;\n"+
   "assert \"a\".get(0).minus(2) == 95;\n";

   private static final String SOURCE_4 =
   "assert \"hello\".map(e -> e.toUpperCase()) == 'HELLO';\n"+
   "assert \"hello\".map(_.toUpperCase()) == 'HELLO';\n";

   private static final String SOURCE_5 =
   "assert \"hello\".take(2) == \"he\";\n"+
   "assert \"hello\".take(221) == \"hello\";\n"+
   "assert \"hello\".takeRight(2) == \"lo\";\n"+
   "assert \"hello\".takeRight(12) == \"hello\";\n";

   private static final String SOURCE_6 =
   "assert \"hello\".distinct() == \"helo\";\n"+
   "assert \"hello\".sort() == \"ehllo\";\n"+
   "assert \"hello\".filter(c -> c == 'l') == \"ll\";\n"+
   "assert \"hello\".filterNot(c -> c == 'l') == \"heo\";\n"+
   "assert \"hello\".reverse() == \"olleh\";\n";

   private static final String SOURCE_7 =
   "assert \"hello\".sliding(2) == [\"he\", \"el\", \"ll\", \"lo\"];\n"+
   "assert \"hello\".sliding(3) == [\"hel\", \"ell\", \"llo\"];\n";

   public void testEach() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      Timer.timeExecution("testListFill", executable);
   }

   public void testExplicitCast() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      Timer.timeExecution("testExplicitCast", executable);
   }

   public void testAt() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      Executable executable = compiler.compile(SOURCE_3);
      Timer.timeExecution("testAt", executable);
   }

   public void testMap() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_4);
      Executable executable = compiler.compile(SOURCE_4);
      Timer.timeExecution("testMap", executable);
   }

   public void testHeadTail() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_5);
      Executable executable = compiler.compile(SOURCE_5);
      Timer.timeExecution("testHeadTail", executable);
   }

   public void testReverseFilterSort() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_6);
      Executable executable = compiler.compile(SOURCE_6);
      Timer.timeExecution("testReverseFilterSort", executable);
   }

   public void testSliding() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_7);
      Executable executable = compiler.compile(SOURCE_7);
      Timer.timeExecution("testSliding", executable);
   }
}