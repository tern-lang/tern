package org.ternlang.compile;

import junit.framework.TestCase;

public class YieldTest extends TestCase {
   
   private static final String SOURCE_1 =
   "function fun(){\n"+
   "   var i = 0;\n"+
   "   while(i++ < 10){\n"+
   "      yield i;\n"+
   "   }\n"+
   "}\n"+
   "var it = fun();\n"+
   "for(x in it){\n"+
   "   println(x);\n"+
   "}\n"+
   "var x = fun().iterator();\n"+
   "assert x.next() == 1;\n"+
   "assert x.next() == 2;\n";      
   
   private static final String SOURCE_2 =
   "function fun(){\n"+
   "   var i = 0;\n"+
   "   while(i++ < 10){\n"+
   "      if(i %2 == 0) {\n"+
   "         yield `even=${i}`;\n"+
   "      } else {\n"+
   "         yield `odd=${i}`;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "var it = fun();\n"+
   "for(x in it){\n"+
   "   println(x);\n"+
   "}\n"+
   "var x = fun().iterator();\n"+
   "assert x.next() == 'odd=1';\n"+
   "assert x.next() == 'even=2';\n";   
   
   private static final String SOURCE_3 =
   "function fun(){\n"+
   "   var i = 0;\n"+
   "   while(i++ < 10){\n"+
   "      for(var j = 0; j < 10; j++){\n"+
   "         yield `j=${j} i=${i}`;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "var it = fun();\n"+
   "for(x in it){\n"+
   "   println(x);\n"+
   "}\n"+
   "var x = fun().iterator();\n"+
   "assert x.next() == 'j=0 i=1';\n"+
   "assert x.next() == 'j=1 i=1';\n"+    
   "assert x.next() == 'j=2 i=1';\n"+
   "assert x.next() == 'j=3 i=1';\n";
   
   private static final String SOURCE_4 =
   "function fun(){\n"+
   "   var i = 0;\n"+
   "   while(i++ < 10){\n"+
   "      for(var j in 0..10){\n"+
   "         if(j % 2==0) {\n"+
   "            yield `even j=${j} i=${i}`;\n"+
   "         } else {\n"+
   "            yield `odd j=${j} i=${i}`;\n"+
   "         }\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "var it = fun();\n"+
   "for(x in it){\n"+
   "   println(x);\n"+
   "}\n"+
   "var x = fun().iterator();\n"+
   "assert x.next() == 'even j=0 i=1';\n"+
   "assert x.next() == 'odd j=1 i=1';\n"+    
   "assert x.next() == 'even j=2 i=1';\n"+
   "assert x.next() == 'odd j=3 i=1';\n";
   
   private static final String SOURCE_5 =
   "function fun(){\n"+
   "   if(true) {\n"+
   "      yield 'hello';\n"+
   "      yield 'world';\n"+
   "   }\n"+
   "}\n"+
   "var it = fun();\n"+
   "for(x in it){\n"+
   "   println(x);\n"+
   "}\n"+
   "var x = fun().iterator();\n"+
   "assert x.next() == 'hello';\n"+
   "assert x.next() == 'world';\n";   
   
   private static final String SOURCE_6 =
   "function fun(a){\n"+
   "   for(i in 0..9){\n"+
   "      yield i;\n"+
   "      yield `i=${i} a=${a}`;\n"+
   "   }\n"+
   "}\n"+
   "for(x in fun(11)){\n"+
   "   println(x);\n"+
   "}\n"+
   "var it = fun(11).iterator();\n"+
   "assert it.next() == 0;\n"+
   "assert it.next() == 'i=0 a=11';\n"+
   "assert it.next() == 1;\n"+
   "assert it.next() == 'i=1 a=11';\n"+
   "assert it.next() == 2;\n"+
   "assert it.next() == 'i=2 a=11';\n"+
   "assert it.next() == 3;\n"+
   "assert it.next() == 'i=3 a=11';\n"+
   "assert it.next() == 4;\n"+
   "assert it.next() == 'i=4 a=11';\n";    
   
   private static final String SOURCE_7 = 
   "function fun(a){\n"+
   "   yield 'a';\n"+
   "   if(a != null) {\n"+
   "      synchronized(a){\n"+
   "         yield 1;\n"+
   "         yield 2;\n"+
   "         yield 3;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "for(i in fun('x')){\n"+
   "   println(i);\n"+
   "}\n"+
   "var it = fun('x').iterator();\n"+
   "assert it.next() == 'a';\n"+
   "assert it.next() == 1;\n"+
   "assert it.next() == 2;\n"+
   "assert it.next() == 3;\n"; 
   
   private static final String SOURCE_8 =
   "function fun(a){\n"+
   "   try {\n"+
   "      yield `1=${a}`;\n"+
   "      yield `2=${a}`;\n"+
   "      yield `3=${a}`;\n"+
   "   }catch(e){\n"+
   "      e.printStackTrace();\n"+
   "   }\n"+
   "   yield 'done';\n"+
   "}\n"+
   "for(i in fun('y')){\n"+
   "   println(i);\n"+
   "}\n"+
   "var it = fun('y').iterator();\n"+
   "assert it.next() == '1=y';\n"+
   "assert it.next() == '2=y';\n"+
   "assert it.next() == '3=y';\n"+   
   "assert it.next() == 'done';\n";
   
   private static final String SOURCE_9 =
   "function fun(a){\n"+
   "   switch(a){\n"+
   "      case 'a':\n"+
   "         yield 'A';\n"+
   "         break;\n"+
   "      case 'b':\n"+
   "         yield 'B';\n"+
   "         yield 'B2';\n"+
   "      case 'c':\n"+
   "         yield 'C';\n"+
   "         break;\n"+
   "      default:\n"+
   "         yield 'DEFAULT';\n"+
   "   }\n"+
   "   yield 'done';\n"+
   "}\n"+
   "for(x in fun('b')){\n"+
   "   println(x);\n"+
   "}\n"+
   "var it = fun('b').iterator();\n"+
   "assert it.next() == 'B';\n"+
   "assert it.next() == 'B2';\n"+
   "assert it.next() == 'C';\n"+
   "assert it.next() == 'done';\n";

   private static final String SOURCE_10 =
   "function fun(){\n"+
   "   var i = 0;\n"+
   "   until(i++ >= 10){\n"+
   "      for(var j in 0..10){\n"+
   "         if(j % 2==0) {\n"+
   "            yield `even j=${j} i=${i}`;\n"+
   "         } else {\n"+
   "            yield `odd j=${j} i=${i}`;\n"+
   "         }\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "var it = fun();\n"+
   "for(x in it){\n"+
   "   println(x);\n"+
   "}\n"+
   "var x = fun().iterator();\n"+
   "assert x.next() == 'even j=0 i=1';\n"+
   "assert x.next() == 'odd j=1 i=1';\n"+
   "assert x.next() == 'even j=2 i=1';\n"+
   "assert x.next() == 'odd j=3 i=1';\n";

   public void testYieldInWhileLoop() throws Exception {
      System.out.println(SOURCE_1);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }

   public void testYieldInIfStatement() throws Exception {
      System.out.println(SOURCE_2);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
   
   public void testYieldInForStatement() throws Exception {
      System.out.println(SOURCE_3);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      executable.execute();
   }
   
   public void testYieldInForInStatement() throws Exception {
      System.out.println(SOURCE_4);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      executable.execute();
   }
   
   public void testYieldInCompoundStatement() throws Exception {
      System.out.println(SOURCE_5);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      executable.execute();
   }
   
   public void testDoubleYieldInForInStatement() throws Exception {
      System.out.println(SOURCE_6);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_6);
      executable.execute();
   }
   
   public void testYieldInSynchronizedStatement() throws Exception {
      System.out.println(SOURCE_7);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_7);
      executable.execute();
   }   

   public void testYieldInTryCatchStatement() throws Exception {
      System.out.println(SOURCE_8);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_8);
      executable.execute();
   } 
   
   public void testYieldInSwitchStatement() throws Exception {
      System.out.println(SOURCE_9);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_9);
      executable.execute();
   }

   public void testYieldInUntilStatement() throws Exception {
      System.out.println(SOURCE_10);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_10);
      executable.execute();
   }
}
