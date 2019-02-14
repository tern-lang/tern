package org.ternlang.compile;

import junit.framework.TestCase;

public class FunctionScopeTest extends TestCase {

   private static final String SOURCE_1 =
   "function checkWithClosureCall() {\n"+
   "   var x = 0;\n"+
   "   var func = (a, b) -> isVariableInScope(a, b);\n"+
   "\n"+
   "   assert !func(1,2);\n"+ // not found?
   "}\n"+
   "\n"+
   "function isVariableInScope(a, b) {\n"+
   "   try {\n"+
   "      x++;\n"+ // trying to access 'x' which is not in scope causes an exception
   "      println('OK');\n"+
   "      return true;\n"+
   "   }catch(e){\n"+
   "      e.printStackTrace();\n"+
   "   }\n"+
   "   return false;\n"+
   "\n"+
   "}\n"+
   "checkWithClosureCall();\n";
   
   private static final String SOURCE_2 =
   "var global = 1;\n"+
   "function foo(){\n"+
   "   var x = 11;\n"+
   "   var canSeeX = canSeeX(x+1);\n"+
   "   assert x == 11;\n"+
   "   return canSeeX;\n"+
   "}\n"+
   "function canSeeX(b){\n"+
   "   assert b == 12;\n"+
   "   global++;\n"+
   "   try{\n"+
   "      x++;\n"+
   "      return true;\n"+
   "   }catch(e){\n"+
   "      e.printStackTrace();\n"+
   "   }\n"+
   "   return false;\n"+
   "}\n"+
   "assert foo() == false;\n"+
   "assert global == 2;\n";

   
   public void testFunctionScope() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      
      try {
         System.err.println(SOURCE_1);
         Executable executable = compiler.compile(SOURCE_1);
         executable.execute();
      } catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Compile failure", failure);
   }
   
   public void testFunctionCallingFunctionInScope() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      
      try {
         System.err.println(SOURCE_2);
         Executable executable = compiler.compile(SOURCE_2);
         executable.execute();
      } catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Compile failure", failure);
   }
}
