package org.ternlang.compile.staticanalysis;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;

import junit.framework.TestCase;

public class CastSubTypeTest extends TestCase {

   private static final String SOURCE_1 = 
   "class A{\n"+
   "   func(){\n"+
   "      if(this instanceof C){\n"+
   "         (this as C).call();\n"+
   "      }\n"+
   "      if(this instanceof B){\n"+
   "         (this as B).bar();\n"+
   "      }\n"+   
   "   }\n"+
   "}\n"+
   "class B extends A{\n"+
   "   bar(){\n"+
   "      println('B.bar');\n"+
   "   }\n"+
   "}\n"+
   "class C extends B{\n"+
   "   call(){\n"+
   "      println('C.call');\n"+
   "   }\n"+
   "}\n"+
   "new C().func();\n";
   
   private static final String SOURCE_2 = 
   "class A{\n"+
   "   func(){\n"+
   "     this.call();\n"+
   "   }\n"+
   "}\n"+
   "class B extends A{\n"+
   "   bar(){\n"+
   "      println('B.bar');\n"+
   "   }\n"+
   "}\n"+
   "class C extends B{\n"+
   "   call(){\n"+
   "      println('C.call');\n"+
   "   }\n"+
   "}\n"+
   "new C().func();\n";
   
   private static final String SOURCE_3 = 
   "class A{\n"+
   "   func(){\n"+
   "     call();\n"+
   "   }\n"+
   "}\n"+
   "class B extends A{\n"+
   "   bar(){\n"+
   "      println('B.bar');\n"+
   "   }\n"+
   "}\n"+
   "class C extends B{\n"+
   "   call(){\n"+
   "      println('C.call');\n"+
   "   }\n"+
   "}\n"+
   "new C().func();\n";
   
   public void testSubType() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
   }
   
   public void testSubTypeCompileErrorWithThisCall() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = true;
      try {
         System.err.println(SOURCE_2);
         compiler.compile(SOURCE_2).execute();
      }catch(Exception e) {
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Compile error", failure);
   }
   
   public void testSubTypeCompileErrorWithLocalCall() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = true;
      try {
         System.err.println(SOURCE_3);
         compiler.compile(SOURCE_3).execute();
      }catch(Exception e) {
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Compile error", failure);
   }
         
}
