package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class ReferenceTypeTest extends TestCase{ 
   
   private static final String SOURCE=
   "println(Integer[].class);";

   public void testReferenceType() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
