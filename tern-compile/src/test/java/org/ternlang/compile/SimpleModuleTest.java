package org.ternlang.compile;

import junit.framework.TestCase;

public class SimpleModuleTest extends TestCase {
   
   private static final String SOURCE =
   "module Mod{\n"+
   "   public const x=1;\n"+
   "   public const y=x+1;\n"+
   "   if(y>x){\n"+
   "      println('y>x');\n"+
   "   }\n"+
   "}\n"+
   "println(Mod.x);\n"+
   "println(Mod.y);\n";

   public void testModuleProperties() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }

}
