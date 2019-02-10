package tern.compile;

import junit.framework.TestCase;

public class DebugStatementTest extends TestCase {

   private static final String SOURCE_1 =
   "for(i in 0..10){\n"+
   "   debug i == 7;\n"+
   "}\n";

   private static final String SOURCE_2 =
   "for(i in 0..10){\n"+
   "   debug;\n"+
   "}\n";
   
   public void testDebug() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   } 
   
   public void testDebugNoCondition() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   } 
}
