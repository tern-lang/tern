package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class CompoundTest extends TestCase {

   private static final String SOURCE=
   "var i = 1;\n"+
   "if(i>0){\n"+
   "  var x = i;\n"+
   "  x++;\n"+
   "}\n";

   public void testCompoundStatement() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();

   }
}