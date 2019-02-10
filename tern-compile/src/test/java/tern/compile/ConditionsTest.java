package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class ConditionsTest extends TestCase {
   
   private static final String SOURCE =
   "var a=[1,2,3];\n"+
   "var leftIndex=0;\n"+
   "var partionElement=2;\n"+
   "var right=33;\n"+
   "var i=0;\n"+
   "while((leftIndex < right) && (a[leftIndex] < partionElement)){\n"+
   "   leftIndex++;\n"+
   "}\n"+
   "println(i);\n";

   public void testCompoundStatement() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   
   }
}
