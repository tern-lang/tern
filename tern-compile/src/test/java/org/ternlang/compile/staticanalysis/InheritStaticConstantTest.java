package org.ternlang.compile.staticanalysis;

import junit.framework.TestCase;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;

public class InheritStaticConstantTest extends TestCase {
   
   private static final String SOURCE_1 = 
   "trait Nuh{\n"+
   "   const VAL3=3;\n"+
   "}\n"+
   "trait Blah extends Nuh{\n"+
   "   const VAL11 =8+VAL3;\n"+
   "   const VAL12 = 12;\n"+
   "}\n"+
   "class Foo with Blah{\n"+
   "   static const VAL12PLUSVAL11 = VAL11+VAL12;\n"+
   "   static get(){\n"+
   "      return VAL12PLUSVAL11;\n"+
   "   }\n"+
   "}\n"+
   "println(Foo.VAL12PLUSVAL11);";
   
   public void testStaticConstants() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
   }
}
