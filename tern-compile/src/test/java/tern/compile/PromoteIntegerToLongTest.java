package tern.compile;

import junit.framework.TestCase;

public class PromoteIntegerToLongTest extends TestCase {
   
   private static final String SOURCE_1 =
   "var ok = 2147483647;\n"+
   "var tooBig = 2247483647;\n"+
   "\n"+
   "assert ok.class === Integer.class;\n"+
   "assert tooBig.class == Long.class;\n"+
   "assert tooBig > ok;\n"+
   "println(tooBig);\n"+
   "println(ok);\n";

   private static final String SOURCE_2 =
   "var json = `{\n"+
   "   \"value1\": 2247483647,\n"+
   "   \"value2\": -2247483647,\n"+
   "   \"value3\": -247483647,\n"+
   "   \"value4\": 47483647\n"+
   "}`;\n"+
   "var result = eval(json);\n"+
   "assert result.value1.class == Long.class;\n"+
   "assert result.value2.class == Long.class;\n"+
   "assert result.value3.class == Integer.class;\n"+
   "assert result.value4.class == Integer.class;\n"+
   "assert result.value1 > result.value4;\n";
   
   private static final String SOURCE_3 =
   "var json = `{\n"+
   "   \"value1\": 2247483647,\n"+
   "   \"value2\": -2247483647,\n"+
   "   \"value3\": -247483647L,\n"+
   "   \"value4\": 47483647L\n"+
   "}`;\n"+
   "var result = eval(json);\n"+
   "assert result.value1.class == Long.class;\n"+
   "assert result.value2.class == Long.class;\n"+
   "assert result.value3.class == Long.class;\n"+
   "assert result.value4.class == Long.class;\n"+
   "assert result.value1 > result.value4;\n";
   
   public void testPromotion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testPromotionFromEval() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
   
   public void testPromotionFromEvalWithSuffix() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      Executable executable = compiler.compile(SOURCE_3);
      executable.execute();
   }
}
