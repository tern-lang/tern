package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class ReflectionTest extends TestCase {
   
   private static final String SOURCE =
   "module Mod{\n"+
   "   @Test\n"+
   "   f(){\n"+
   "   }\n"+
   "}\n"+
   "Mod.getFunctions().stream().forEach(f -> println(f.getAnnotations()));\n";
         
   
   public void testReflection() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
