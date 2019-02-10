package tern.compile;

import junit.framework.TestCase;

public class ModuleInitTest extends TestCase {

   private static final String SOURCE_1 =
   "assert Fud.x != 0;\n"+
   "println(Fud.x);\n"+
   "module Fud {\n"+
   "   var x=0;\n"+
   "   for(i in 0..10){\n"+
   "      x+=i;\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_2 =
   "Fud.func();\n"+
   "module Fud {\n"+
   "   var x=0;\n"+
   "   for(i in 0..10){\n"+
   "      x+=i;\n"+
   "   }\n"+
   "   func(){\n"+
   "      assert x != 0;\n"+
   "      println(x);\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_3 =
   "Fud.func();\n"+
   "println(Fud.x);\n"+
   "module Fud {\n"+
   "   var x=0;\n"+
   "   for(i in 0..10){\n"+
   "      x+=i;\n"+
   "   }\n"+
   "   func(){\n"+
   "      assert x != 0;\n"+
   "      println(x);\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_4 =
   "var x = new Fud.Foo();\n"+
   "x.func();\n"+
   "module Fud {\n"+
   "   var x = 10;\n"+
   "   println('fud');\n"+
   "   class Foo{\n"+
   "      func(){\n"+
   "         println('func:'+Fud.x);\n"+
   "      }\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_5 =
   "module Fud {\n"+
   "   var x = 10;\n"+
   "   println('fud');\n"+
   "   class Foo{\n"+
   "      func(){\n"+
   "         println('func:'+Fud.x);\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "class Typ{\n"+
   "   call(){\n"+
   "      var x = new Fud.Foo();\n"+
   "      x.func();\n"+
   "      assert Fud.x == 10;\n"+
   "   }\n"+
   "}"+
   "new Typ().call();";

   public void testInitBeforeVariableReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testInitBeforeFunctionCall() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
   
   public void testInitWithVariableReferenceAndCall() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      Executable executable = compiler.compile(SOURCE_3);
      executable.execute();
   }
   
   public void testInitWithInnerClass() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      executable.execute();
   }
   
   public void testInitWithInnerClassFromOtherClass() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      executable.execute();
   }
}
