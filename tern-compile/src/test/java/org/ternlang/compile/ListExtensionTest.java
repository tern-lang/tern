package org.ternlang.compile;

import junit.framework.TestCase;

public class ListExtensionTest extends TestCase {

   private static final String SOURCE_1 =
   "assert [1, 2, 3, 4, 5, 3, 4].indexOfSlice([3, 4], 4) == 5;\n"+
   "assert [1, 2, 3, 4, 5, 3, 4].indexOfSlice([3], 4) == 5;\n"+
   "assert [1, 2, 3, 4, 5, 3, 4].indexOfSlice([], 4) == 4;\n"+
   "assert [1, 2, 3, 4, 5].indexOfSlice([3, 4]) == 2;\n"+
   "assert [1, 2, 3, 4, 5].indexOfSlice([8, 4]) == -1;\n";

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
   "assert [1, 2, 3].disjoint([3, 4, 5]) == [1, 2, 4, 5];\n";

   private static final String SOURCE_6 =
   "assert [].fill(0, 6)(44) == [44, 44, 44, 44, 44, 44];\n"+
   "assert [].fill(3)(11) == [11, 11, 11];\n"+
   "assert [].fill(3)(11).distinct() == {11};\n";

   private static final String SOURCE_7 =
   "let l = [1 .. 5].shuffle();\n"+
   "assert l.max() == 5;\n" +
   "assert l.min() == 1;\n";

   private static final String SOURCE_8 =
   "let take3 = [1 .. 5].take(3);\n"+
   "let take10 = [1 .. 5].take(10);\n"+
   "let drop44 = [0 .. 50].drop(44);\n"+
   "let dropRight44 = [0 .. 50].dropRight(44);\n"+
   "let take77reverse = [0 .. 5].take(77).reverse();\n"+
   "assert take3.length == 3;\n" +
   "assert take3 == [1, 2, 3];\n"+
   "assert take10 == [1, 2, 3, 4, 5];\n"+
   "assert drop44.length == 7;\n" +
   "assert drop44 == [44, 45, 46, 47, 48, 49, 50];\n"+
   "assert dropRight44.length == 7;\n" +
   "assert dropRight44 == [0, 1, 2, 3, 4, 5, 6];\n"+
   "assert take77reverse == [5, 4, 3, 2, 1, 0];\n";

   private static final String SOURCE_9 =
   "let l = [1 .. 5].map(i -> i + 1);\n"+
   "let l2 = [1 .. 5].map(i -> 'a'.toCharacter() + i);\n"+
   "assert l == [2, 3, 4, 5, 6];\n" +
   "assert l2 == [98, 99, 100, 101, 102];\n";

   private static final String SOURCE_10 =
   "[1 .. 5].zip().forEach(e -> {\n"+
   "   println(e);\n"+
   "   assert e.value == e.source[e.index];\n"+
   "});\n"+
   "[1 .. 5].zip([3, 7, 23]).forEach(e -> {\n"+
   "   println(e);\n"+
   "   assert e.value(0) == e.source(0)[e.index];\n"+
   "   assert e.value(1) == e.source(1)[e.index];\n"+
   "});\n";

   private static final String SOURCE_11 =
   "assert [0 .. 5].sliding(2) == [[0, 1], [1, 2], [2, 3], [3, 4], [4, 5]];\n"+
   "assert [0 .. 5].sliding(3) == [[0, 1, 2], [1, 2, 3], [2, 3, 4], [3, 4, 5]];\n";

   private static final String SOURCE_12 =
   "class Adder {\n"+
   "   add(a, b) {\n"+
   "      return a + b;\n"+
   "   }\n"+
   "}\n"+
   "const adder = Adder();\n"+
   "assert [2, 3, 4].fold(1)((a, b) -> a + b) == 10;\n"+
   "assert [1 .. 5].fold(1)((a, b) -> a + b) == 16;\n"+
   "assert [0 .. 5].fold(1)((a, b) -> a + b) == 16;\n"+
   "assert [0 .. 5].fold(0)(adder::add) == 15;\n"+
   "assert [1, 2, 3].foldLeft(1)((a, b) -> a + b) == 7;\n"+
   "assert [1, 2, 3].foldLeft([])((a, b) -> {a.add(b + 1); return a;}) == [2, 3, 4];\n"+
   "assert [1, 2, 3].foldLeft([])((a, b) -> a.plus(b + 1)) == [2, 3, 4];\n"+
   "assert {1, 2, 3}.foldLeft({})((a, b) -> a.plus(b + 1)) == {2, 3, 4};\n";

   private static final String SOURCE_13 =
   "assert [0 .. 12].filter(i -> i % 2 == 0) == [0, 2, 4, 6, 8, 10, 12];\n";

   private static final String SOURCE_14 =
   "assert [1, 2, 3, 4, 5, 3, 4].indexWhere(_ == 3, 4) == 5;\n"+
   "assert [1, 2, 3, 4, 5, 3, 4].indexWhere(_ % 2 == 0) == 1;\n"+
   "assert [1, 2, 3, 4, 5, 3, 4].indexWhere(_ == 3) == 2;\n";

   public void testIndexOfSlice() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      Timer.timeExecution("testIndexOfSlice", executable);
   }

   public void testListReverse() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      Timer.timeExecution("testListReverse", executable);
   }

   public void testListMax() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      Executable executable = compiler.compile(SOURCE_3);
      Timer.timeExecution("testListMax", executable);
   }

   public void testListClone() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_4);
      Executable executable = compiler.compile(SOURCE_4);
      Timer.timeExecution("testListClone", executable);
   }

   public void testDisjoint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_5);
      Executable executable = compiler.compile(SOURCE_5);
      Timer.timeExecution("testDisjoint", executable);
   }

   public void testFillFromTo() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_6);
      Executable executable = compiler.compile(SOURCE_6);
      Timer.timeExecution("testFillFromTo", executable);
   }

   public void testMinMaxShuffle() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_7);
      Executable executable = compiler.compile(SOURCE_7);
      Timer.timeExecution("testMinMaxShuffle", executable);
   }

   public void testTakeAndDrop() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_8);
      Executable executable = compiler.compile(SOURCE_8);
      Timer.timeExecution("testTakeAndDrop", executable);
   }

   public void testMap() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_9);
      Executable executable = compiler.compile(SOURCE_9);
      Timer.timeExecution("testMap", executable);
   }

   public void testZip() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_10);
      Executable executable = compiler.compile(SOURCE_10);
      Timer.timeExecution("testZip", executable);
   }

   public void testSlidingWindow() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_11);
      Executable executable = compiler.compile(SOURCE_11);
      Timer.timeExecution("testSlidingWindow", executable);
   }

   public void testFold() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_12);
      Executable executable = compiler.compile(SOURCE_12);
      Timer.timeExecution("testFold", executable);
   }

   public void testFilter() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_13);
      Executable executable = compiler.compile(SOURCE_13);
      Timer.timeExecution("testFilter", executable);
   }

   public void testIndexWhere() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_14);
      Executable executable = compiler.compile(SOURCE_14);
      Timer.timeExecution("testIndexWhere", executable);
   }
}