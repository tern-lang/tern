package org.ternlang.compile;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

import junit.framework.TestCase;

public class NewInstanceTest extends TestCase {

   private static final String SOURCE = 
   "class SuperClass{\n"+
   "   static const SUPER1 = 11;\n"+
   "   static const SUPER2 = 12;\n"+
   "   const super1;\n"+
   "   const super2;\n"+
   "   new(super1, super2){\n"+
   "      this.super1 = super1;\n"+
   "      this.super2 = super2;\n"+
   "   }\n"+
   "   dump(){\n"+
   "      println(\"${super1},${super2}\");\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class ThisClass extends SuperClass {\n"+
   "   static const THIS1 = 11;\n"+
   "   static const THIS2 = 12;\n"+
   "   const this1;\n"+
   "   new(this1) : super(this1, this1){\n"+
   "      this.this1 = this1;\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "var v = new ThisClass(1);\n"+
   "v.dump();\n";
   
   public void testNewInstance() throws Exception{
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
         
}
