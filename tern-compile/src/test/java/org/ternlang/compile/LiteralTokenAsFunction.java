package org.ternlang.compile;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

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
