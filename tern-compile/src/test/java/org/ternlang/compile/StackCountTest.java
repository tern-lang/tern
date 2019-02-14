package org.ternlang.compile;

import junit.framework.TestCase;

public class StackCountTest extends TestCase {
   
   private static final String SOURCE =
   "var x =10;\n"+
   "if(x > 0){\n"+
   "   var y = x;\n"+
   "   y++;\n"+
   "}\n"+
   "if(x  >0){\n"+
   "   var y = 0;\n"+
   "   y++;\n"+
   "}\n"+
   "if(x !=77){\n"+
   "   if(x > 0){\n"+
   "      var y =1;\n"+
   "      y--;\n"+
   "   } else {\n"+
   "      var y=33;\n"+
   "      y++;\n"+
   "   }\n"+
   "}\n";
   
   public void testStackCount() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      System.err.println(SOURCE);
      executable.execute();
   }

}
