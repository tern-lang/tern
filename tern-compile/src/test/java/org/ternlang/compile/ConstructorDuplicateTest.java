package org.ternlang.compile;

import junit.framework.TestCase;

public class ConstructorDuplicateTest extends TestCase {

   private static final String SOURCE =
   "class Vec{\n"+
   "   var x:Number;\n"+
   "   var y:Number;\n"+
   "   var z:Number;\n"+
   "   new(x:Number, y:Number, z: Number){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "      this.z=z;\n"+
   "   }\n"+
   "}\n"+
   "var v = new Vec(1,1.1,-0.2);\n"+
   "println(v.x);\n";

   public void testConstructorDuplicate() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
