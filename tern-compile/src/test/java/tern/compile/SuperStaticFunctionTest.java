package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class SuperStaticFunctionTest extends TestCase {

   private static final String SOURCE =
   "class Base with Runnable {\n"+
   "   static const BLAH = new Base(11);\n"+
   "   var x;\n"+
   "   new(x){\n"+
   "      println(\"Base.new(${x})\");\n"+
   "      this.x=x;\n"+
   "   }\n"+
   "   static get(){\n"+
   "      return BLAH;\n"+
   "   }\n"+
   "   run(){}\n"+
   "}\n"+
   "class Nuh extends Base {\n"+
   "   static const DEFAULT_SIZE=1;\n"+
   "   static const INSTANCE = new Nuh(44);\n"+
   "   var a;\n"+
   "   new(a): super(DEFAULT_SIZE){\n"+
   "      this.a=a;\n"+
   "      println(\"Nuh.new(${a})\");\n"+
   "   }\n"+
   "   static get(){\n"+
   "      return INSTANCE;\n"+
   "   }\n"+
   "\n"+
   "}\n"+
   "var b = Base.get();\n"+
   "println(b.x);\n"+
   "\n"+
   "var n = Nuh.get();\n"+
   "println(n.x);\n"+
   "println(n.class);\n"+
   "println(n.a);\n";

   public void testStaticVariable() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }       
}
