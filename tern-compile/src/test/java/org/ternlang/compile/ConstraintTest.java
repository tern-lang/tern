package org.ternlang.compile;

import junit.framework.TestCase;

public class ConstraintTest extends TestCase {

   private static final String SOURCE_1 =
   "var x: String = 'hello';\n"+
   "var y: Integer = 1;\n"+
   "y++;\n"+
   "y.hashCode();\n"+
   "println(x);\n";
   
   private static final String SOURCE_2 =
   "System.err.println('x');";
   
   private static final String SOURCE_3 = 
   "class Foo {\n"+
   "   foo(x): List{\n"+
   "      return [];\n"+
   "   }\n"+
   "}\n"+
   "new Foo().foo(1).foo();\n";

   public void testCompoundStatement() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   
   }

   public void testStaticReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   
   }
   
   public void testInvalidReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      
      try{
         Executable executable = compiler.compile(SOURCE_3);
         executable.execute();
      }catch(Exception e){
         failure = true;
         e.printStackTrace();
      }
      assertTrue("Compile error", failure);
   
   }
}