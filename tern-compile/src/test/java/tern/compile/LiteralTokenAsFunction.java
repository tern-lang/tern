package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class LiteralTokenAsFunction extends TestCase {

   private static final String SOURCE =
   "function with(){\n"+
   "   println(11);\n"+
   "}\n"+
   "with();";
   
   public void testLiteralAsFunction() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   
   }
}
