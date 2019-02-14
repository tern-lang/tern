package org.ternlang.compile;

import junit.framework.TestCase;

public class LocalReplacesScopeTest extends TestCase {
   
   private static final String SOURCE =
   "class Foo{\n"+
   "   var x = 66;\n"+
   "   func(){\n"+
   "      var x = x+1;\n"+
   "      assert x == 67;\n"+
   "   }\n"+
   "}\n"+
   "new Foo().func();";

   public void testReplaceScope() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute();
   }
}
