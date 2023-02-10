package org.ternlang.compile;

import static org.ternlang.core.Reserved.GRAMMAR_SCRIPT;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import org.ternlang.core.Reserved;
import org.ternlang.parse.SyntaxCompiler;
import org.ternlang.parse.SyntaxNode;

import com.sun.management.ThreadMXBean;

public class ClosureTest extends ScriptTestCase {

   private static final String SOURCE_1=
   "var x = (a,b)->{ return a+b;};\n"+
   "var r = x(1,33);\n"+
   "println(r);";
   
   private static final String SOURCE_2=
   "max((a,b)->{return a+b;});\n"+
   "\n"+
   "function max(f){\n"+
   "   var x = f('xx', 'bb');\n"+
   "   println(x);\n"+
   "}\n";
   
   private static final String SOURCE_3=
   "max((a,b)->a+b);\n"+
   "\n"+
   "function max(f){\n"+
   "   var x = f('xx', 'bb');\n"+
   "   println(x);\n"+
   "}\n";

   private static final String SOURCE_4=
   "var expression = x->x>0;\n"+
   "var f = -> {\n"+
   "   expression(10);\n"+
   "};\n";
   
   private static final String SOURCE_5=
   "let x = (a) -> a.run();\n"+
   "println(x);\n";

   private static final String SOURCE_6=
   "let x = async (a) -> 'x';\n"+
   "println(x.class);\n"+
   "println(x(1).class);\n"+
   "assert x(1) instanceof Promise;\n"+
   "assert x(1).value == 'x';\n";

   private static final String SOURCE_7=
   "let x = async (a) -> {" +
   "   return await System.currentTimeMillis();\n"+
   "};\n" +
   "println(x.class);\n"+
   "x(1).success(y -> println(y)).join();\n"+
   "assert x(1).value instanceof Long;\n";

   private static final String SOURCE_8 =
   "let list = {'key-1': 'val-1', 'key-2': 'val-2', 'key-3': 'val-3'}.entrySet()\n" +
   "   .stream()\n"+
   "   .map(e -> {'entry.key': e.key, 'entry.value': e.value})\n"+
   "   .iterator()\n"+
   "   .toList();\n"+
   "\n"+
   "assert list[0]['entry.key'] == 'key-1';\n"+
   "assert list[0]['entry.value'] == 'val-1';\n"+
   "assert list[1]['entry.key'] == 'key-2';\n"+
   "assert list[1]['entry.value'] == 'val-2';\n"+
   "assert list[2]['entry.key'] == 'key-3';\n"+
   "assert list[2]['entry.value'] == 'val-3';\n";
   
   private static final String SOURCE_9 =
   "function time(name, func){\n"+
   "   let thread = Thread(-> {\n"+
   "      println(Thread.currentThread().name);\n"+
   "      var s = System.currentTimeMillis();\n"+
   "      func();\n"+
   "      var f = System.currentTimeMillis();\n"+
   "      println(name +': '+(f-s));\n"+
   "   });\n"+
   "   thread.start();\n"+
   "   thread.join();\n"+
   "}\n"+
   "time('foo', -> {\n"+
   "   for(i in 0 to 10) {\n"+
   "      println(i);\n"+
   "   }\n"+
   "});\n";
   
   public void testClosure() throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
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
   
   public void testClosureAsParameter() throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
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
   
   public void testClosureEvaluationAsParameter() throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      System.err.println(SOURCE_3);
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
   
   public void testClosureInClosure() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      System.err.println(SOURCE_4);
      executable.execute();
   }
   
   public void testClosureParameters() throws Exception {
      SyntaxNode node = new SyntaxCompiler(Reserved.GRAMMAR_FILE).compile().parse("/path.tern", SOURCE_5, GRAMMAR_SCRIPT);
      System.out.println(SyntaxPrinter.print(node));
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      System.err.println(SOURCE_5);
      executable.execute();
   }

   public void testAsyncClosure() throws Exception {
      SyntaxNode node = new SyntaxCompiler(Reserved.GRAMMAR_FILE).compile().parse("/path.tern", SOURCE_6, GRAMMAR_SCRIPT);
      System.out.println(SyntaxPrinter.print(node));
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_6);
      System.err.println(SOURCE_6);
      executable.execute();
   }

   public void testAsyncWithBlockClosure() throws Exception {
      SyntaxNode node = new SyntaxCompiler(Reserved.GRAMMAR_FILE).compile().parse("/path.tern", SOURCE_7, GRAMMAR_SCRIPT);
      System.out.println(SyntaxPrinter.print(node));
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_7);
      System.err.println(SOURCE_7);
      executable.execute();
   }

   public void testIterateOverMapEntries() throws Exception {
      SyntaxNode node = new SyntaxCompiler(Reserved.GRAMMAR_FILE).compile().parse("/path.tern", SOURCE_8, GRAMMAR_SCRIPT);
      System.out.println(SyntaxPrinter.print(node));
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_8);
      System.err.println(SOURCE_8);
      executable.execute();
   }
   
   public void testClosureInThread() throws Exception {
      SyntaxNode node = new SyntaxCompiler(Reserved.GRAMMAR_FILE).compile().parse("/path.tern", SOURCE_8, GRAMMAR_SCRIPT);
      System.out.println(SyntaxPrinter.print(node));
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_9);
      System.err.println(SOURCE_9);
      executable.execute();
   }
}