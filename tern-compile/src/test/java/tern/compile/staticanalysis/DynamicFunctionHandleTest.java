package tern.compile.staticanalysis;

import tern.compile.ClassPathCompilerBuilder;
import tern.compile.Compiler;

import junit.framework.TestCase;

public class DynamicFunctionHandleTest extends TestCase {
   
   private static final String SOURCE_1 =
   "var map = {a: [Math.class]};\n"+
   "var f = map.a[0]::max;\n"+
   "var result = f(7,33);\n"+
   "\n"+
   "assert result == 33;\n";   
   
   private static final String SOURCE_2 =
   "var list = [Math.class];\n"+
   "var func1 = list[0]::max;\n"+
   "var func2 = list[0]::min;\n"+
   "var result1 = func1(1,23);\n"+
   "var result2 = func2(1,23);\n"+
   "\n"+
   "assert result1 == 23;\n"+
   "assert result2 == 1;\n";

   public void testReferenceArrayHandle() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
   }
   
   public void testArrayHandle() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute();
   }
}
