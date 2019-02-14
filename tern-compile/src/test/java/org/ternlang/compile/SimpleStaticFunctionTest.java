package org.ternlang.compile;

import junit.framework.TestCase;

public class SimpleStaticFunctionTest extends TestCase {

   private static final String SOURCE =
   "class Foo{\n"+
   "   static ret(index){\n"+
   "      return index;\n"+
   "   }\n"+
   "}\n"+
   "Foo.ret(11);\n";
   
   public void testReferenceStaticParameter() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }

}
