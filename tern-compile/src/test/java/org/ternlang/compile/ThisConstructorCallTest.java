package org.ternlang.compile;

import junit.framework.TestCase;

public class ThisConstructorCallTest extends TestCase {
   
   private static final String SOURCE =
   "class Blah {\n"+
   "   var a;\n"+
   "   var b;\n"+
   "\n"+
   "   new(a) : this(a, 10){}\n"+
   "   new(a, b) {\n"+
   "      this.a=a;\n"+
   "      this.b=b;\n"+
   "   }\n"+
   "   dump(){\n"+
   "      System.err.println(\"a=\"+a+\" b=\"+b);\n"+
   "   }\n"+
   "}\n"+
   "var b1 = new Blah(\"one-arg\");\n"+
   "var b2 = new Blah(\"two-arg\", \"other-arg\");\n"+
   "\n"+
   "b1.dump();\n"+
   "b2.dump();\n";


         
   public void testThisCall() throws Exception{
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new ThisConstructorCallTest().testThisCall();
   }
}
