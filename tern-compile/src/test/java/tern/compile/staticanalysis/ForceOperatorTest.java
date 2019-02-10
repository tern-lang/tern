package tern.compile.staticanalysis;

import junit.framework.TestCase;

import tern.compile.ClassPathCompilerBuilder;
import tern.compile.Compiler;
import tern.compile.verify.VerifyException;

public class ForceOperatorTest extends TestCase {

   private static final String SOURCE_1 =
   "class Foo{\n"+
   "   func(){\n"+
   "   }\n"+
   "}\n"+
   "class Bar extends Foo{\n"+
   "   foo(){\n"+
   "   }\n"+
   "}\n"+   
   "var x: Foo = new Bar();\n"+
   "x.func();\n"+
   "x.foo();\n";

   private static final String SOURCE_2 =
   "class Foo{\n"+
   "   func(){\n"+
   "   }\n"+
   "}\n"+
   "class Bar extends Foo{\n"+
   "   foo(){\n"+
   "   }\n"+
   "}\n"+
   "var x: Foo = new Bar();\n"+
   "x.func();\n"+
   "x!.foo();\n";
   
   private static final String SOURCE_3 =
   "class Foo{\n"+
   "   func(){\n"+
   "      this!.foo();\n"+
   "   }\n"+
   "}\n"+
   "class Bar extends Foo{\n"+
   "   foo(){\n"+
   "      println('Bar.foo()');\n"+
   "   }\n"+
   "}\n"+
   "var x: Foo = new Bar();\n"+
   "x.func();\n";         
   
   public void testCompileAsNoFunctionMatches() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      boolean failure = true;
      
      try {
         compiler.compile(SOURCE_1).execute();
      } catch(VerifyException e) {
         e.getErrors().get(0).getCause().printStackTrace();
         String message =  e.getErrors().get(0).getDescription();
         assertEquals("Function 'foo()' not found for 'default.Foo' in /default.snap at line 11", message);
         failure = true;
      }
      assertTrue("Should be a compile error", failure);
   }
   
   public void testSuccessWhenForced() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute();
   }
   
   public void testSuccessWhenForcedForThis() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      compiler.compile(SOURCE_3).execute();
   }  
}
