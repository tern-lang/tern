package org.ternlang.compile;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

import junit.framework.TestCase;

public class CallableClosureTest extends TestCase {
   
   private static final String SOURCE = 
   "var list = [];\n"+
   "for(var i = 0; i < 100; i++){\n"+
   "   list[i] = () -> println(i);\n"+
   "}\n"+
   "for(var i = 0; i < 100; i++){\n"+
   "   var f = list[i];\n"+
   "   f();\n"+
   "}\n";
         
   
//   private int y = 2;
//   public void testClosure() throws Exception {
//      ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);
//      List<Future> list = new ArrayList<Future>();
//      int n = 0;
//      for(int i = 0; i < 100; i++) {
//         int x = i;
//         Runnable r = () -> System.err.println(x + n + y);
//         list.add(executor.submit(r));
//         y++;
//      }
//      for(Future f : list){
//         f.get();
//      }
//   }
   
   public void testClosureScope() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }   
}
