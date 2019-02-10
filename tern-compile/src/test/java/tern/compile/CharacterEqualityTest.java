package tern.compile;

import junit.framework.TestCase;

public class CharacterEqualityTest extends TestCase {
   
   private static final String SOURCE =
   "var char1: Character = \"text\".charAt(0);\n"+
   "var char2: Character = \"text\".charAt(0);\n"+
   "assert char1 == 't';\n"+
   "assert 't' == char1;\n"+
   "assert char2 == 't';\n"+
   "assert 't' == char2;\n"+   
   "assert 't' == 't';\n"+
   "assert char1 == char2;\n"+
   "assert char1 == char1;\n"+
   "assert char1 === char1;\n"+
   "assert char2 === char2;\n"+
   "assert char1 !== char2;\n"+
   "assert char2 !== char1;\n";   

   public void testEquality() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
   
}
