package org.ternlang.compile;

import junit.framework.TestCase;

public class InstantiateNoExistantClassTest extends TestCase {
   
   private static final String SOURCE =
   "var v = new NonExistantClass();\n"+
   "//v.dump(1,2);\n";
   
   public void testHierarchy() throws Exception {
      boolean failure = false;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      try {
         executable.execute();
      }catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Should not be able to create non existent type", failure);
   }
}
