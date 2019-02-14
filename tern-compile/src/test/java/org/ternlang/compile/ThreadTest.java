package org.ternlang.compile;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

import junit.framework.TestCase;

public class ThreadTest extends TestCase {

   private static final String SOURCE_1 =
//   "import util.concurrent.ScheduledThreadPoolExecutor;\n"+
//   "import util.concurrent.TimeUnit;\n"+
//   "\n"+
//   "const waitOnApprTimerTask = new TimerCounterTask(0);\n"+
//   "const waitOnApprTimer = new ScheduledThreadPoolExecutor(2);\n"+
//   "\n"+
//   "waitOnApprTimer.scheduleAtFixedRate(waitOnApprTimerTask, 0, 2 * 1000, TimeUnit.MILLISECONDS);\n"+
//   "\n"+
   "class TimerCounterTask with Runnable {\n"+
   "   var counter;\n"+
   "   new(counter){\n"+
   "      this.counter = counter;\n"+
   "   }\n"+
   "   run() {\n"+
   "      println('Still waiting ... ' + counter);\n"+
   "      counter++;\n"+
   "  }\n"+
   "}\n";
   
   public void testScheduler() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
      executable.execute();
   }

}
