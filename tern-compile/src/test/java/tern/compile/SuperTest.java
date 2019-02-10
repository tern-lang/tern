package tern.compile;

import junit.framework.TestCase;

public class SuperTest extends TestCase {
   
   private static final String SOURCE =
   "class Base {\n"+
   "   var a;\n"+
   "   var b;\n"+
   "   new(a,b){\n"+
   "      this.a = a;\n"+
   "      this.b = b;\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class Concrete extends Base {\n"+
   "   new(a, b) : super(a, b){\n"+
   "      System.err.println(\"a=\"+a+\" b=\"+b);\n"+
   "   }\n"+
   "   dump(){\n"+
   "      System.err.println(\"dump(a=\"+a+\" b=\"+b+\")\");\n"+
   "   }\n"+
   "}\n"+
   "var c = new Concrete(1,2);\n"+
   "c.dump();\n";

         
   public void testSuper() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new SuperTest().testSuper();
   }
}
