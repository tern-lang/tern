package tern.compile;

import junit.framework.TestCase;

public class StaticReferenceChainTest extends TestCase {
  
   private static final String SOURCE_1=
   "class X{}\n"+
   "class Y {\n"+
   "   static const DEFAULT = 11;\n"+
   "   const radius = DEFAULT;\n"+
   "}\n"+
   "var y = new Y();";

         
   public void testStaticVariable() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new StaticReferenceChainTest().testStaticVariable();
   }

}
