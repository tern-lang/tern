package org.ternlang.compile;

import junit.framework.TestCase;

public class FunctionalInterfaceTest extends TestCase {

   private static final String SOURCE_1=
   "invoke(() -> println('runnable invoked!!'));\n"+
   "\n"+
   "function invoke(r: Runnable) {\n"+
   "   r.run();\n"+
   "}\n";
   
   private static final String SOURCE_2=
   "blah((a)->Double.compare(a,11));\n"+
   "blah(()->println('runnable invoked'));\n"+
   "\n"+
   "function blah(r: Runnable) {\n"+
   "   r.run();\n"+
   "}\n"+
   "\n"+
   "function blah(c: Comparable)  {\n"+
   "   var v = c.compareTo(11);\n"+
   "   println(v);\n"+
   "}\n";

   private static final String SOURCE_3=
   "var t = new Thread(()->println('thread run invoked'));\n"+
   "t.start();\n"+
   "t.join();\n";
   
   private static final String SOURCE_4=
   "var set = new TreeSet((a,b)->Double.compare(a,b));\n"+
   "\n"+
   "set.add(1.2);\n"+
   "set.add(2.3);\n"+
   "set.add(33.4);\n"+
   "set.add(4.55);\n"+
   "set.add(2);\n"+
   "\n"+
   "for(var entry in set){\n"+
   "   println(entry);\n"+
   "}\n";
   
   private static final String SOURCE_5=
   "var set = new TreeSet(Double::compare);\n"+
   "\n"+
   "set.add(1.2);\n"+
   "set.add(2.3);\n"+
   "set.add(33.4);\n"+
   "set.add(4.55);\n"+
   "set.add(2);\n"+
   "\n"+
   "for(var entry in set){\n"+
   "   println(entry);\n"+
   "}\n";

   public void testRunnable() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
      executable.execute();
   }
   
   public void testFunctionalMatch() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
      executable.execute();
   }
   
   public void testFunctionalMatchThread() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      System.err.println(SOURCE_3);
      executable.execute();
   }
   
   public void testFunctionalMatchTreeSet() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      System.err.println(SOURCE_4);
      executable.execute();
   }
   
   public void testFunctionalReferenceMatchTreeSet() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      System.err.println(SOURCE_5);
      executable.execute();
   }
}