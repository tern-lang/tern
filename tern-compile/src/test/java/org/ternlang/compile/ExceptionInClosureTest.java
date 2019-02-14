package org.ternlang.compile;

import junit.framework.TestCase;

public class ExceptionInClosureTest extends TestCase {
   
   private static final String SOURCE =
   "var func = (a: Integer, b) -> {\n"+
   "   throw new Exception(\"a=${a} b=${b}\");\n"+
   "};\n"+
   "\n"+
   "function call(func: (a,b)){\n"+
   "   func(1,2);\n"+
   "}\n"+
   "function doIt(){\n"+
   "   call(func);\n"+
   "}\n"+
   "doIt();\n";

   public void testExceptionInClosure() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         Executable executable = compiler.compile(SOURCE);
         executable.execute();
      }catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Exception was thrown", failure);
   }
}
