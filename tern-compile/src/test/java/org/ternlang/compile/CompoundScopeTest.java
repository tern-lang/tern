package org.ternlang.compile;

import org.ternlang.core.scope.EmptyModel;

import junit.framework.TestCase;

public class CompoundScopeTest extends TestCase {
   
   private static final String SOURCE =
   "function fun(){\n"+
   "  for(var i = 0; i < 10000000; i++){\n"+
   "     assert i >-1;\n"+
   "  }\n"+
   "}\n"+
   "fun();";


   public void testScope() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
