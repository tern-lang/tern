package tern.compile;

import junit.framework.TestCase;

public class SuperFunctionTest extends TestCase {
   
   private static final String SOURCE =
   "class A {\n"+
   "   var x;\n"+
   "   new(x){\n"+
   "      var i = 0;\n"+
   "      i++;\n"+
   "      println('A.new('+x+')->'+class);\n"+
   "      this.x=x;\n"+
   "   }\n"+
   "   fun1(){\n"+
   "      println('A.fun1()='+x);\n"+
   "      fun3();\n"+
   "   }\n"+
   "   fun3(){\n"+
   "      println('A.fun3()='+x);\n"+
   "   }\n"+
   "}\n"+
   "class B extends A {\n"+
   "   new(x):super(x){\n"+
   "      println('B.new('+x+')->'+class);\n"+
   "   }\n"+
   "}\n"+
   "class C extends B {\n"+
   "   new(x):super(x){\n"+
   "      println('C.new('+x+')->'+class);\n"+
   "   }\n"+
   "   fun2() {\n"+
   "      println('C.fun2()='+x);\n"+
   "      super.fun1();\n"+
   "   }\n"+
   "   fun3() {\n"+
   "      println('C.fun3()='+x);\n"+
   "   }\n"+
   "}\n"+
   "class D extends C {\n"+
   "   new(x) :super(x){\n"+
   "      println('D.new('+x+')->'+class);\n"+
   "   }\n"+
   "}\n"+
   "var d = new D(11);\n"+
   "d.fun2();\n";
         
   public void testSuperFunction() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
