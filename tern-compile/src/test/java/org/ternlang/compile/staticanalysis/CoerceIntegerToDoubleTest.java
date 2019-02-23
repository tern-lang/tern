package org.ternlang.compile.staticanalysis;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;

import junit.framework.TestCase;

public class CoerceIntegerToDoubleTest extends TestCase {
   
   private static final String SOURCE_1 = 
   "var x: Integer[] = [123,4,44];\n"+
   "assert x[2].class == Integer.class;\n"+
   "var y: Double[] = x;\n"+
   "assert y[2].class == Double.class;\n";
         
   public void testArrayCoercion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
   }
}
