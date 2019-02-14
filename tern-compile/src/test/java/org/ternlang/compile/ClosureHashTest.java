package org.ternlang.compile;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

import junit.framework.TestCase;

import com.sun.management.ThreadMXBean;

public class ClosureHashTest extends TestCase {

   private static final String SOURCE=
   "var func1 = () -> println('func1()');\n"+
   "var func2 = (a) -> println(a);\n"+
   "var set = {func1, func2, func2};\n"+
   "\n"+
   "set.contains(func2);\n"+  
   "set.contains(func1);\n";

   public void testClosureHash() throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      System.err.println(SOURCE);
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
}
