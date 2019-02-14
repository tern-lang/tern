package org.ternlang.compile;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

import junit.framework.TestCase;

public class InstanceOfTest extends TestCase {

   private static final String SOURCE_1=
   "class X{}\n"+
   "class Y extends X{}\n"+
   "var y = new Y();\n"+
   "\n"+
   "assert y instanceof X;\n"+
   "assert {:}instanceof Map;\n"+
   "assert {}instanceof Set;\n"+
   "assert 1 instanceof Integer;\n"+
   "assert 2f instanceof Float;\n"+
   "assert \"\"!instanceof null;";
   
   private static final String SOURCE_2 =
   "class Task{}\n"+
   "var a = new Task[1];\n"+
   "var b = new Integer[1];\n"+
   "var c = new String[1][2];\n"+
   "assert a instanceof Task[];\n"+
   "assert b instanceof Integer[];\n"+
   "assert c instanceof String[][];\n"+
   "assert a !instanceof null;\n"+
   "assert null!instanceof Integer.class;\n";         

   public void testInstanceOf() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
      executable.execute();
   }
   
   public void testInstanceOfArray() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
      executable.execute();
   }
}
