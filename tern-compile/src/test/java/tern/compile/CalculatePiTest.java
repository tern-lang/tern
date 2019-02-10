package tern.compile;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import junit.framework.TestCase;

import com.sun.management.ThreadMXBean;

public class CalculatePiTest extends TestCase{
   
   private static final String SOURCE =
   "var x = 0;\n"+
   "var pi = 4;\n"+
   "var plus = false;\n"+
   "for (var i = 3; i < 10000000; i += 2)\n"+
   "{\n"+
   "    if (plus)\n"+
   "    {\n"+
   "        pi += 4.0 / i;\n"+
   "    }\n"+
   "    else\n"+
   "    {\n"+
   "        pi -= 4.0 / i;\n"+
   "    }\n"+
   "    plus = !plus;\n"+
   "}\n";
         
   public void testCalculation() throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      ThreadMXBean bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
      Thread thread = Thread.currentThread();
      long id = thread.getId();
      System.gc();
      System.gc();
      Thread.sleep(100);
      long before = bean.getThreadAllocatedBytes(id);
      long start = System.currentTimeMillis();
      executable.execute();
      long finish = System.currentTimeMillis();
      long after = bean.getThreadAllocatedBytes(id);
      System.out.println();
      System.out.println("time=" + (finish - start) + " memory=" + format.format(after - before));
   }

   public static void main(String[] list) throws Exception {
      new CalculatePiTest().testCalculation();
   }
}
