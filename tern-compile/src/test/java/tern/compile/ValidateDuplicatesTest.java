package tern.compile;

import junit.framework.TestCase;

public class ValidateDuplicatesTest extends TestCase {
   
   private static final String SOURCE =
   "class Foo {\n"+
   "   testMethod(a){}\n"+
   "   foo(){}\n"+
   "   testMethod(){}\n"+
   "   testMethod(a){}\n"+
   "}\n"+
   "new Foo().foo();\n";
   
   public void testValidateErrorOnDuplicates() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      boolean failure = false;
      try {
         executable.execute();
      }catch(Exception e){
         failure=true;
         e.printStackTrace();
      }
      assertTrue("Duplicate method should cause failure", failure);
   }
}
