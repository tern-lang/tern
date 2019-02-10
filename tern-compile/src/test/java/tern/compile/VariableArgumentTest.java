package tern.compile;

import junit.framework.TestCase;

public class VariableArgumentTest extends TestCase {
   
   private static final String SOURCE_1 =
   "function fun(a, b...) {\n"+
   "   println(a);\n"+
   "}\n"+
   "fun(1,2,3,4,5);\n";
   
   private static final String SOURCE_2 =
   "function fun(a: Long, b...: Double) {\n"+
   "   println(a);\n"+
   "   for(var i in b) {\n"+
   "      println('b='+i);\n"+
   "   }\n"+
   "   println(b.size());\n"+
   "}\n"+
   "\n"+
   "fun(1,22,33,44,55);\n";
   
   private static final String SOURCE_3 =
   "function fun(a: Long, b...: Boolean) {\n"+
   "   println(a);\n"+
   "   for(var i in b) {\n"+
   "      println('b='+i);\n"+
   "   }\n"+
   "   println(b.size());\n"+
   "}\n"+
   "\n"+
   "fun(1,true,33,44,55);\n";
         
   private static final String SOURCE_4 =
   "var v = sum(13, 44, 234, 1, 3);\n"+
   "\n"+
   "function sum(numbers...){ // variable arguments\n"+
   "   var sum = 0;\n"+
   "\n"+
   "   for(var number in numbers){\n"+
   "      sum += number;\n"+
   "   }\n"+
   "   return sum;\n"+
   "}\n";
   
   private static final String SOURCE_5 =
   "function fun(a, b...) {\n"+
   "   println(a);\n"+
   "}\n";
         
   public void testVariableArguments() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }

   public void testVariableArgumentsWithConstraint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
   
   public void testVariableArgumentsWithFailure() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      boolean failure = false;
      try {
         executable.execute();
      } catch(Throwable e) {
         failure = true;
         e.printStackTrace();
      }
      assertTrue(failure);
   }
   
   public void testVariableArgumentsSum() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      executable.execute();
   }
   
   public static void main(String[] list) throws Exception {
      new VariableArgumentTest().testVariableArguments();
      new VariableArgumentTest().testVariableArgumentsWithConstraint();
      new VariableArgumentTest().testVariableArgumentsWithFailure();
      new VariableArgumentTest().testVariableArgumentsSum();
   }
   
   public void testVariableArgumentDeclaration() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      executable.execute();
   }
}
