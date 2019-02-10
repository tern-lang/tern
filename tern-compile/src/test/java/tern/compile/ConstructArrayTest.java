package tern.compile;

import junit.framework.TestCase;

public class ConstructArrayTest extends TestCase {
   
   private static final String SOURCE=
   "class X{\n"+
   "   var x;\n"+
   "   new(x){\n"+
   "      this.x=x;\n"+
   "   }\n"+
   "}\n"+
   "var arr=new X[10][10];\n"+
   "System.err.println(arr);\n";

   public void testConstruct() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new ConstructArrayTest().testConstruct();
   }

}
