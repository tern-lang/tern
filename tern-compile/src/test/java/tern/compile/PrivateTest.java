package tern.compile;

import junit.framework.TestCase;

public class PrivateTest extends TestCase {

   private static final String SOURCE =
   "class Test{\n"+
   "   private var x=0;\n"+
   "   var y=1;\n"+
   "   private const p=33;\n"+
   "}\n"+
   "var t = new Test();\n";
         
   public void testPrint() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
}
