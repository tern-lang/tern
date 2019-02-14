package org.ternlang.compile;

import junit.framework.TestCase;

public class SuperScopeTest extends TestCase {

   private static final String SOURCE =
   "class A {\n"+
   "   var a;\n"+
   "   new(a){\n"+
   "      this.a=a;\n"+
   "   }\n"+
   "   fun1(){\n"+
   "      println(\"A.fun1(): a=\"+a);\n"+
   "   }\n"+
   "   test(){\n"+
   "      fun1();\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class B extends A {\n"+
   "   var b;\n"+
  // "   var s = this;\n"+ // SHOULD THIS BE LEGAL??????
   "   new(a,b):super(a){\n"+
   "      this.b=b;\n"+
   "   }\n"+
   "   fun1(){\n"+
   "      println(\"B.fun1(): b=\"+b);\n"+
   "   }\n"+
   "   test(){\n"+
   "      super.test();\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "var b = new B(1,2);\n"+
   "b.test();\n";
   
   public void testSuperScope() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new SuperScopeTest().testSuperScope();
   }
         
}
