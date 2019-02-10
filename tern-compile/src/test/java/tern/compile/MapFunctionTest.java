package tern.compile;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

import com.sun.management.ThreadMXBean;

@SuppressWarnings("restriction")
public class MapFunctionTest extends TestCase {
   
   private static final String SOURCE =
   "var map = {\n"+
   "   a: \"aa\",\n"+
   "   b: \"bb\",\n"+
   "   blah: (x)->println(\"blah ${x}\"),\n"+
   "   foo: (x,y)->println(\"foo x=${x} y=${y}\")\n"+
   "};\n"+
   "\n"+
   "println(map.a);\n"+
   "println(map);\n"+
   "map.foo(1, 22);\n";
   
   public void testMapFunction() throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
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

}
