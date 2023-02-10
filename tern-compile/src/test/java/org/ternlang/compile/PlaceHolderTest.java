package org.ternlang.compile;

public class PlaceHolderTest extends ScriptTestCase {

   private static final String SOURCE_1=
   "assert List.of('1.1').map(_.split('\\.')).map(_[0]).toString() == '[1]';\n";

   private static final String SOURCE_2=
   "println(['a','b','c'].map(_.toUpperCase()));\n";

   private static final String SOURCE_3=
   "['xx%s'].forEach(printf(_, 1));\n"+
   "[12, 13, 14].forEach(printf('(%s)', _));\n";

   private static final String SOURCE_4=
   "println([12].map(_ + 2));\n"+
   "assert [12, 13, 14].map(_ + 2) == [14, 15, 16];\n";

   private static final String SOURCE_5=
   "assert [12, 13, 14].filter(_ > 12) == [13, 14];\n"+
   "assert [0 .. 4].filter(_ > 2) == [3, 4];\n"+
   "println([0 .. 4].filter(_ > 2));\n"+
   "println([0 .. 4].map(_ > 2));\n"+
   "assert [0 .. 4].map(_ > 2) == [false, false, false, true, true];\n"+
   "assert [0 .. 4].filter(_ % 2 == 0) == [0, 2, 4];\n";

   private static final String SOURCE_6=
   "assert [0 .. 4].filter(_ % 2 == 0) == [0, 2, 4];\n";

   private static final String SOURCE_7=
   "assert [0 .. 10].filter(_ == 1 || _ == 2) == [1, 2];\n";

   private static final String SOURCE_8=
   "println(['a', 'bbb'].map(_.length + 1));\n"+
   "assert ['a', 'bbb'].map(_.length + 1) == [2, 4];\n"+
   "assert ['a', 'bb'].map(_ + _.length + 1) == ['a11', 'bb21'];\n"+
   "assert ['a', 'bb'].map(_ + (_.length + 1)) == ['a2', 'bb3'];\n";

   private static final String SOURCE_9=
   "assert [[1, 1],[2, 3],[3, 8]].map(_[1]) == [1, 3, 8];\n"+
   "assert ['hello', 'world'].map(_[1]).toString() == '[e, o]';\n"+
   "assert ['hello', 'world'].map(_[1].toUpperCase()).toString() == '[E, O]';\n"+
   "assert ['hello', 'world'].map(_.charAt(1)).toString() == '[e, o]';\n"+
   "['hello', 'world'].forEach(printf('%s-%s%n', _[0], _[1].toUpperCase()));\n";

   public void testPlaceHolder() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
      executable.execute();
   }

   public void testPlaceHolderMap() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
      executable.execute();
   }

   public void testExpansion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      System.err.println(SOURCE_3);
      executable.execute();
   }

   public void testExpansionCalculation() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      System.err.println(SOURCE_4);
      executable.execute();
   }

   public void testExpansionComparison() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      System.err.println(SOURCE_5);
      executable.execute();
   }

   public void testExpansionComparisonAndCalculation() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_6);
      System.err.println(SOURCE_6);
      executable.execute();
   }

   public void testRelationalOperator() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_7);
      System.err.println(SOURCE_7);
      executable.execute();
   }

   public void testExpansionOfReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_8);
      System.err.println(SOURCE_8);
      executable.execute();
   }

   public void testExpansionOfIndex() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_9);
      Executable executable = compiler.compile(SOURCE_9);
      executable.execute();
   }
}