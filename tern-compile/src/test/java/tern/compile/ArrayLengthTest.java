package tern.compile;

import junit.framework.TestCase;

public class ArrayLengthTest extends TestCase {

   private static final String SOURCE =
   "var a=new Byte[10];\n"+
   "function f(arr){\n"+
   "  for(var i = 0; i < arr.length; i++){};\n"+
   "}\n"+
   "f(a);\n";
   
   public void testLength() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
