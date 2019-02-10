package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class ModuleClassReferenceTest extends TestCase {

   private static final String SOURCE =
   "module Blah{\n"+
   "   class Foo{\n"+
   "      var a;\n"+
   "      var b;\n"+
   "      new(a,b){\n"+
   "         this.a=a;\n"+
   "         this.b=b;\n"+
   "      }\n"+
   "      toString(){\n"+
   "         return \"${a}=${b}\";\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "var x = new Blah.Foo(1,2);\n"+
   "println(x);\n";
   
   public void testReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.out.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
         
}
