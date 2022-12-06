package org.ternlang.compile;

import junit.framework.TestCase;

public class ListExtensionTest extends TestCase {

   private static final String SOURCE_1 =
   "let l = [0 .. 256].fill(2);\n" +
   "println(l);\n"+
   "assert l[44] == 2;\n";

   private static final String SOURCE_2 =
   "let l = [0 .. 5].reverse();\n" +
   "println(l);\n"+
   "assert l == [5, 4, 3, 2, 1, 0];\n";

   private static final String SOURCE_3 =
   "assert [0 .. 5].max() == 5;\n";

   private static final String SOURCE_4 =
   "let l = [0 .. 5].reverse();\n"+
   "assert l.clone().max() == 5;\n" +
   "assert l == [5, 4, 3, 2, 1, 0];\n";

   private static final String SOURCE_5 =
   "assert [0 .. 5].disjoint([6 .. 10]);\n";

   private static final String SOURCE_6 =
   "assert [].fill(44, 0, 6) == [44, 44, 44, 44, 44, 44];\n"+
   "assert [].fill(11, 3) == [11, 11, 11];\n";

   private static final String SOURCE_7 =
   "let l = [1 .. 5].shuffle();\n"+
   "assert l.max() == 5;\n" +
   "assert l.min() == 1;\n";

   private static final String SOURCE_8 =
   "let h = [1 .. 5].head(3);\n"+
   "let h2 = [1 .. 5].head(10);\n"+
   "let t = [0 .. 50].tail(7);\n"+
   "let t2 = [0 .. 5].tail(77).reverse();\n"+
   "assert h.length == 3;\n" +
   "assert h == [1, 2, 3];\n"+
   "assert t.length == 7;\n" +
   "assert t == [44, 45, 46, 47, 48, 49, 50];\n"+
   "assert h2 == [1, 2, 3, 4, 5];\n"+
   "assert t2 == [5, 4, 3, 2, 1, 0];\n";

   private static final String SOURCE_9 =
   "let l = [1 .. 5].map(i -> i.value + 1);\n"+
   "let l2 = [1 .. 5].map(i -> 'a'.toCharacter() + i.value);\n"+
   "assert l == [2, 3, 4, 5, 6];\n" +
   "assert l2 == [98, 99, 100, 101, 102];\n";

   private static final String SOURCE_10 =
   "[1 .. 5].each(e -> {\n"+
   "   println(e);\n"+
   "   assert e.value == e.source[e.index];\n"+
   "});\n";

   private static final String SOURCE_11 =
   "assert [0 .. 5].sliding(2) == [[0, 1], [1, 2], [2, 3], [3, 4], [4, 5]];\n"+
   "assert [0 .. 5].sliding(3) == [[0, 1, 2], [1, 2, 3], [2, 3, 4], [3, 4, 5]];\n";
//
//   public void testListFill() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_1);
//      Executable executable = compiler.compile(SOURCE_1);
//      Timer.timeExecution("testListFill", executable);
//   }
//
//   public void testListReverse() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_2);
//      Executable executable = compiler.compile(SOURCE_2);
//      Timer.timeExecution("testListReverse", executable);
//   }
//
//   public void testListMax() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_3);
//      Executable executable = compiler.compile(SOURCE_3);
//      Timer.timeExecution("testListMax", executable);
//   }
//
//   public void testListClone() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_4);
//      Executable executable = compiler.compile(SOURCE_4);
//      Timer.timeExecution("testListClone", executable);
//   }
//
//   public void testDisjoint() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_5);
//      Executable executable = compiler.compile(SOURCE_5);
//      Timer.timeExecution("testDisjoint", executable);
//   }
//
//   public void testFillFromTo() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_6);
//      Executable executable = compiler.compile(SOURCE_6);
//      Timer.timeExecution("testFillFromTo", executable);
//   }
//
//   public void testMinMaxShuffle() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_7);
//      Executable executable = compiler.compile(SOURCE_7);
//      Timer.timeExecution("testMinMaxShuffle", executable);
//   }
//
//   public void testHeadAndTail() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_8);
//      Executable executable = compiler.compile(SOURCE_8);
//      Timer.timeExecution("testHeadAndTail", executable);
//   }
//
//   public void testMap() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_9);
//      Executable executable = compiler.compile(SOURCE_9);
//      Timer.timeExecution("testMap", executable);
//   }
//
//   public void testEach() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_10);
//      Executable executable = compiler.compile(SOURCE_10);
//      Timer.timeExecution("testEach", executable);
//   }

   public void testSlidingWindow() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_11);
      Executable executable = compiler.compile(SOURCE_11);
      Timer.timeExecution("testSlidingWindow", executable);
   }
}