package tern.compile;

import junit.framework.TestCase;

import tern.compile.verify.VerifyException;

public class DuplicateVariableTest extends TestCase {

   private static final String SOURCE =
   "var x = 10;\n"+
   "if(x>2){\n"+
   "   var x = 11;\n"+
   "   x++;\n"+
   "}\n";
   
   public void testDuplicate() throws Exception {
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      boolean failure = false;
      try {
         executable.execute();
      }catch(VerifyException e){
         e.getErrors().get(0).getCause().printStackTrace();
         String message =  e.getErrors().get(0).getDescription();
         assertTrue(message.contains("at line 3"));
         failure = true;
      }
      assertTrue("Duplicate 'x' should cause failure", failure);
   }

}
