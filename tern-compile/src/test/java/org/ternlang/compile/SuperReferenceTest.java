package org.ternlang.compile;

import junit.framework.TestCase;

public class SuperReferenceTest extends TestCase {

   private static final String SOURCE_1=
   "class X{\n"+
   "  f(){\n"+
   "       System.err.println(\"X.f\");\n"+
   "   }\n"+
   "}\n"+
   "class Y extends X{\n"+
   "   f(){\n"+
   "      System.err.println(\"Y.f\");\n"+
   "   }\n"+
   "   t(){\n"+
   "      super.f();\n"+
   "   }\n"+
   "}\n"+
   "var x = new X();\n"+
   "x.f();\n"+
   "var y = new Y();\n"+
   "y.f();\n"+
   "y.t();\n";

         
   public void testSuperReference() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new SuperReferenceTest().testSuperReference();
   }
}
