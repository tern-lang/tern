package tern.compile;

import junit.framework.TestCase;

public class OverrideTest extends TestCase {
  
   private static final String SOURCE_1=
   "class Task with Runnable {\n"+
   "   override run(){\n"+
   "      println('run');\n"+
   "   }\n"+
   "}\n"+
   "var t = new Task();\n"+
   "t.run();";
         
   public void testOverride() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new OverrideTest().testOverride();
   }

}