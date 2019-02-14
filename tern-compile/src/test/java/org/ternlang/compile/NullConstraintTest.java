package org.ternlang.compile;

import junit.framework.TestCase;

public class NullConstraintTest extends TestCase {

   private static final String SOURCE =
   "class Tile {\n"+
   "   var x;\n"+
   "   new(x){\n"+
   "      this.x=x;\n"+
   "   }\n"+
   "}\n"+
   "function ok(): Tile{\n"+
   "   return new Tile(1);\n"+
   "}\n"+
   "function isNull(): Tile{\n"+
   "   return null;\n"+
   "}\n"+
   "var x: Tile = ok();\n"+
   "var y: Tile = isNull();\n"+
   "\n"+
   "println(x);\n"+
   "println(y);\n";
   
   public void testNullConstraint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }

}
