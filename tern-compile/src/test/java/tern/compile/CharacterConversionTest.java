package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class CharacterConversionTest extends TestCase {

   private static final String SOURCE_1 =
   "var x = \"\\r\";"+
   "println(x.length());";   
   
   private static final String SOURCE_2 =
   "println(\"\\r\".length());";   
   
   public void testLength() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testLiteralLength() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
}
