package tern.compile;

import junit.framework.TestCase;

public class ForAsWhileTest extends TestCase {

   private static final String SOURCE =
   "var x = 0;\n"+
   "for(;x<40;){\n"+
   "   println(x++);\n"+
   "}";
   
   public void testForAsWhile() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
