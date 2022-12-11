package org.ternlang.compile;

public class PlaceHolderTest extends ScriptTestCase {

   private static final String SOURCE_1=
   "println(['1.1'].map(_.split('.')));\n";

   private static final String SOURCE_2=
   "println(['a','b','c'].map(_.toUpperCase()));\n";

   private static final String SOURCE_3=
   "['xx%s'].forEach(printf(_, 1));\n"+
   "[12, 13, 14].forEach(printf('(%s)', _));\n";

   private static final String SOURCE_4=
   "println([12, 13, 14].map(_ + 2));\n"+
   "assert [12, 13, 14].map(_ + 2) == [14, 15, 16];\n";

   private static final String SOURCE_5=
   "assert [12, 13, 14].filter(_ > 12) == [13, 14];\n";

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
}