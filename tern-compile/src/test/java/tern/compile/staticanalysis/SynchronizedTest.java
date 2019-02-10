package tern.compile.staticanalysis;

import junit.framework.TestCase;

import tern.compile.ClassPathCompilerBuilder;
import tern.compile.Compiler;

public class SynchronizedTest extends TestCase {

   private static final String SOURCE =
   "class X{\n"+
   "   foo(){\n"+
   "      synchronized(null){\n"+
   "         println('x');\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "new X().foo();\n";

         
   public void testSynchronizedOnNull() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         System.err.println(SOURCE);
         compiler.compile(SOURCE).execute();
      } catch(Exception e) {
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Synchronize on null", failure);
   }   
}
