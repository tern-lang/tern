package org.ternlang.compile;

import junit.framework.TestCase;

public class ThreadIndexTest extends TestCase {

   private static final String SOURCE =
   "Thread.currentThread().setPriority(Thread.MIN_PRIORITY);";      

   public void testThreadIndex() throws Exception{
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }

}
