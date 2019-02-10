package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class SwitchTest extends TestCase {

   private static final String SOURCE_1 =
   "function testSwitch() {\n"+
   "   var x =\"test\";\n"+
   "   switch(x){\n"+
   "   case \"blah\":\n"+
   "      println(\"blah\");\n"+
   "      break;\n"+
   "   case \"test\":\n"+
   "      println(\"test\");\n"+
   "   case \"foo\":\n"+
   "      println(\"foo\");\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "testSwitch();\n";
   
   private static final String SOURCE_2 =
   "function testSwitch() {\n"+
   "   var x =\"nuh\";\n"+
   "   switch(x){\n"+
   "   case \"blah\":\n"+
   "      println(\"blah\");\n"+
   "      break;\n"+
   "   case \"test\":\n"+
   "      println(\"test\");\n"+
   "   case \"foo\":\n"+
   "      println(\"foo\");\n"+
   "   default:\n"+
   "      println(\"default\");"+
   "   }\n"+
   "}\n"+
   "\n"+
   "testSwitch();\n"; 
   
   private static final String SOURCE_3 =
   "function testSwitch() {\n"+
   "   var x =\"blah\";\n"+
   "   switch(x){\n"+
   "   case \"blah\":\n"+
   "      println(\"blah\");\n"+
   "      break;\n"+
   "   case \"test\":\n"+
   "      println(\"test\");\n"+
   "   case \"foo\":\n"+
   "      println(\"foo\");\n"+
   "   default:\n"+
   "      println(\"default\");"+
   "   }\n"+
   "}\n"+
   "\n"+
   "testSwitch();\n"; 
         
   public void testSwitch() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
      executable.execute();
   }   
   
   public void testSwitchWithDefault() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
      executable.execute();
   }
   
   public void testSwitchWithBreak() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      System.err.println(SOURCE_3);
      executable.execute();
   }
}
