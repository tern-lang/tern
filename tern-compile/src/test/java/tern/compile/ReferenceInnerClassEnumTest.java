package tern.compile;

import junit.framework.TestCase;

public class ReferenceInnerClassEnumTest extends TestCase {

   public static enum Config {
      AAA, BBB, CCC
   }

   private static final String SOURCE =
   "import tern.compile.ReferenceInnerClassEnumTest;\n"+
   "function fun(){\n"+
   "   println(ReferenceInnerClassEnumTest.class);\n"+    
   "   println(ReferenceInnerClassEnumTest.Config.class);\n"+       
   "   println(ReferenceInnerClassEnumTest.Config.AAA);\n"+
   "}\n"+
   "fun();\n";

   public void testInnerEnum() throws Exception {
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
