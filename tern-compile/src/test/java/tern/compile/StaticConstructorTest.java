package tern.compile;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import junit.framework.TestCase;

import com.sun.management.ThreadMXBean;

public class StaticConstructorTest extends TestCase {

   private static final String SOURCE=
   "class X{\n"+
   "   static const DEFAULT_PATTERN = '.*';\n"+
   "   var pattern;\n"+
   "   new() : this(DEFAULT_PATTERN){}"+
   "   new(pattern){\n"+
   "     this.pattern = pattern;\n"+
   "  }\n"+
   "}\n"+
   "var x = new X();\n"+
   "assert x.pattern == '.*';\n"+
   "println(x.pattern);\n";

   public void testThis() throws Exception {
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
      new StaticConstructorTest().testThis();
   }
}
