package org.ternlang.compile;

import junit.framework.TestCase;

import org.ternlang.compile.verify.VerifyException;

public class SynchronizedTest extends TestCase {
   
   private static final String SOURCE =
   "class Blah{\n"+
   "   toString(){\n"+
   "      return 'blah';\n"+
   "   }\n"+
   "}\n"+
   "var blah = new Blah();\n"+
   "synchronized(blah){\n"+
   "   println(blah);\n"+
   "}\n";

   public void testSynchronized() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      try{
         executable.execute();
      }catch(VerifyException e){
         e.getErrors().get(0).getCause().printStackTrace();
         throw e;
      }
   }
}
