package org.ternlang.compile;

import junit.framework.TestCase;

public class ToStringTest extends TestCase {

   private static final String SOURCE = 
   "class Point{\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x = x;\n"+
   "      this.y = y;\n"+
   "   }\n"+
   "   override toString(){\n"+
   "      \"${x},${y}\"; // last expression evaluated returned\n"+
   "   }\n"+
   "}\n"+
   "var point = new Point(null, 1);\n"+
   "var builder = new StringBuilder();\n"+
   "\n"+
   "builder.append(point);\n"+
   "\n"+
   "var text = builder.toString();\n"+
   "println(text);\n"+
   "assert text == 'null,1';\n"+
   "\n"+
   "println(point);\n";
   
   public void testToString() throws Exception {
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
