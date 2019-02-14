package org.ternlang.compile;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

import junit.framework.TestCase;

public class SuperStaticSingletonTest extends TestCase {

   private static final String SOURCE =
   "class Base with Runnable {\n"+
   "   var x;\n"+
   "   new(x){\n"+
   "      println(\"Base.new(${x})\");\n"+
   "      this.x=x;\n"+
   "   }\n"+
   "   override run(){}\n"+
   "}\n"+
   "class Nuh extends Base {\n"+
   "   static const DEFAULT_SIZE=1;\n"+
   "   var a;\n"+
   "   new(a): super(DEFAULT_SIZE){\n"+
   "      this.a=a;\n"+
   "      println(\"Nuh.new(${a})\");\n"+
   "   }\n"+
   "   static get(){\n"+
   "      return new Nuh(11);\n"+
   "   }\n"+
   "\n"+
   "}\n"+
//   "var b = Base.get();\n"+
//   "println(b.x);\n"+
//   "\n"+
   "var n = Nuh.get();\n"+
   "println(n.x);\n"+
   "println(n.class);\n"+
   "println(n.a);\n";

   public void testStaticSingleton() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }       
}
