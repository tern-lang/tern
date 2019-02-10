package tern.compile.staticanalysis;

import junit.framework.TestCase;

import tern.compile.ClassPathCompilerBuilder;
import tern.compile.Compiler;
import tern.compile.verify.VerifyException;

public class FunctionConstraintCompileTest extends TestCase {
   
   private static final String SOURCE_1 = 
   "class Foo{\n"+
   "   blah(x: String, y){\n"+
   "      println(x);\n"+
   "   }\n"+
   "}\n"+
   "function fun(x, c: (a: Foo, b)) {\n"+
   "   c(new Foo(), 1);\n"+
   "}\n"+
   "fun(1, (x: Foo, y) -> println(x));\n";
   
   private static final String SOURCE_2 = 
   "class Foo{\n"+
   "   blah(x: String, y){\n"+
   "      println(x);\n"+
   "   }\n"+
   "}\n"+
   "class Funcs{\n"+
   "   static fun(x, c: (a: Foo, b)) {\n"+
   "      c(new Foo(), 1);\n"+
   "   }\n"+
   "}"+
   "Funcs.fun(1, (x: Foo, y) -> println(x));\n";
   
   private static final String SOURCE_3 = 
   "class Foo{\n"+
   "   blah(x: String, y){\n"+
   "      println(x);\n"+
   "   }\n"+
   "}\n"+
   "class Funcs{\n"+
   "   static fun(x, c: (a: Foo, b, c)) {\n"+
   "      c(new Foo(), 1);\n"+
   "   }\n"+
   "}"+
   "Funcs.fun(1, (x: Foo, y, c) -> println(x));\n";

   public void testFunctionConstraint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
   }
   
   public void testFunctionConstraintInClass() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute();
   }
   
   public void testFunctionConstraintFailure() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      boolean failure = true;
      
      try {
         compiler.compile(SOURCE_3).execute();
      } catch(VerifyException e) {
         e.getErrors().get(0).getCause().printStackTrace();
         String message =  e.getErrors().get(0).getDescription();
         assertEquals("Function 'c(default.Foo, lang.Integer)' not found for 'default.Funcs' in /default.snap at line 8", message);
         failure = true;
      }
      assertTrue("Should be a compile error", failure);
   }
}
