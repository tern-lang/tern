package org.ternlang.compile;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import junit.framework.TestCase;

import com.sun.management.ThreadMXBean;

public class FindTest extends TestCase {

   private static final String SOURCE=
   "module FileFinder {\n"+
   "\n"+
   "   find(root, pattern){\n"+
   "      var file = new File(root);\n"+
   "\n"+
   "      if(file.isDirectory()) {\n"+
   "         return find(file, pattern, []);\n"+
   "      }\n"+
   "      return [file];\n"+
   "   }\n"+
   "\n"+
   "   find(root, pattern, found) {\n"+
   "      var list = root.listFiles((entry) -> {\n"+
   "\n"+
   "         if(entry.isFile()) {\n"+
   "            return entry.name.endsWith(pattern);\n"+
   "         }\n"+
   "         return entry.isDirectory();\n"+
   "      });\n"+
   "      for(var entry in list) {\n"+
   "         if(entry.isDirectory()) {\n"+
   "            find(entry, pattern, found);\n"+
   "         } else {\n"+
   "            found.add(entry);\n"+
   "         }\n"+
   "      }\n"+
   "      return found;\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "var list = FileFinder.find(\"C:\\\\Work\\\\development\\\\github\\\\ngallagher.github.io\", \".tern\");\n"+
   "print(list);\n";
   
   public void testFind() throws Exception {
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

   public static void main(String[] list) throws Exception {
      new FindTest().testFind();
   }
}



