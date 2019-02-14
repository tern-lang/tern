package org.ternlang.compile.staticanalysis;

import junit.framework.TestCase;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;
import org.ternlang.compile.verify.VerifyException;

public class StaticCompileTest extends TestCase {

   private static final String SOURCE_1 = 
   "class Foo{\n"+
   "   static var x:Integer = init();\n"+
   "   static init(): Map {\n"+
   "      return {a: 11};;\n"+
   "   }\n"+
   "}\n"+
   "println('x');";
   
   private static final String SOURCE_2 = 
   "class Foo{\n"+
   "   static func(){\n"+
   "     var f: Integer = init();\n"+
   "     return f;\n"+
   "   }\n"+      
   "   static init(): Map {\n"+      
   "      return null;\n"+
   "   }\n"+
   "}\n"+
   "println('x');";
   
   private static final String SOURCE_3=
   "class A {\n"+
   "   static create(name): B{\n"+
   "      return new B(name);\n"+
   "   }\n"+
   "   class B with Runnable{\n"+
   "      var name;\n"+
   "      new(name){\n"+
   "         this.name=name;\n"+
   "      }\n"+
   "      override run(){}\n"+
   "   }\n"+
   "}\n"+
   "var b = A.create('test');\n"+
   "assert b != null;\n"+
   "println(b);\n";
   
   private static final String SOURCE_4=
   "var b = A.create('test');\n"+
   "assert b != null;\n"+
   "println(b);\n"+
   "class A {\n"+
   "   static create(name): B{\n"+
   "      return new B(name);\n"+
   "   }\n"+
   "   class B with Runnable{\n"+
   "      var name;\n"+
   "      new(name){\n"+
   "         this.name=name;\n"+
   "      }\n"+
   "      override run(){}\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_5 =
   "class X{\n"+
   "   static var f = -> {\n"+
   "      for(i in 3..100)\n"+
   "         yield i;\n"+
   "   };\n"+
   "   static call(){\n"+
   "      return f();\n"+
   "   }\n"+
   "   go(){\n"+
   "      return f();\n"+
   "   }\n"+   
   "}\n"+
   "println(X.f);\n"+
   "var it = X.call().iterator();\n"+
   "var it2 = X.call().iterator();\n"+
   "\n"+
   "assert it.next() == 3;\n"+
   "assert it.next() == 4;\n"+
   "assert it.next() == 5;\n"+
   "assert it.next() == 6;\n"+
   "\n"+
   "assert it2.next() == 3;\n"+
   "assert it2.next() == 4;\n"+
   "assert it2.next() == 5;\n"+
   "assert it2.next() == 6;\n";   
   
   public void testStaticAssignmentCompileError() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         System.err.println(SOURCE_1);
         compiler.compile(SOURCE_1).execute();
      } catch(VerifyException e) {
         e.getErrors().get(0).getCause().printStackTrace();
         String message =  e.getErrors().get(0).getDescription();
         assertEquals(message, "Variable 'x' does not match constraint 'lang.Integer' in /default.tern at line 2");
         failure = true;
      }
      assertTrue("Should be a compile failure", failure);
   }
   
   public void testStaticMethodCompileError() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         System.err.println(SOURCE_2);
         compiler.compile(SOURCE_2).execute();
      } catch(VerifyException e) {
         e.getErrors().get(0).getCause().printStackTrace();
         String message =  e.getErrors().get(0).getDescription();
         assertEquals(message, "Variable 'f' does not match constraint 'lang.Integer' in /default.tern at line 3");
         failure = true;
      }
      assertTrue("Should be a compile failure", failure);

   }
   
   public void testStaticMethodCompile() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      try{
         compiler.compile(SOURCE_3).execute();
      }catch(VerifyException e){
         e.getErrors().get(0).getCause().printStackTrace();
         throw e;
      }
   }
   
   public void testStaticMethodCompileReferencedEarly() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_4);
      try {
         compiler.compile(SOURCE_4).execute();
      }catch(VerifyException e){
         e.getErrors().get(0).getCause().printStackTrace();
         throw e;
      }
   }
   
   public void testStaticClosure() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_5);
      compiler.compile(SOURCE_5).execute();
   }
}
