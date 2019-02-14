package org.ternlang.compile;

import junit.framework.TestCase;

public class NestedForLoopTest extends TestCase {
   
   private static final String SOURCE =
   "var text = ['h', 'w'];\n"+
   "for (var y=0; y < text.length; y++) {\n"+
   "   var line: String = text[y];\n"+ // text is not visible
   "   for (var x=0; x < line.length(); x++) {\n"+
   "      println(line.charAt(x));\n"+
   "   }\n"+
   "}\n"+
   "for (var y=0; y < text.length; y++) {\n"+
   "   var line: String = text[y];\n"+
   "   for (var x=0; x < line.length(); x++) {\n"+
   "      println(line.charAt(x));\n"+
   "   }\n"+
   "}\n";

   public void testFunctionScope() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
