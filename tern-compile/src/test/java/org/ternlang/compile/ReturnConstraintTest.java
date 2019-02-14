package org.ternlang.compile;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

import junit.framework.TestCase;

public class ReturnConstraintTest extends TestCase {

   private static final String SOURCE_1 =
   "class Blah{\n"+
   "   get():Blah{\n"+
   "      return this;\n"+
   "   }\n"+
   "}\n"+
   "function fun(x: Blah):Blah{\n"+
   "   return x.get();\n"+
   "}\n"+
   "var b = new Blah();\n"+
   "fun(b);\n";
   
   private static final String SOURCE_2 =
   "class Blah{\n"+
   "   blah():Byte[] {\n"+
   "      return [1,2,3,4];\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "var object = new Blah();\n"+
   "var list = object.blah();\n"+
   "\n"+
   "println(list.class);\n";


   public void testReturnConstraint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testConstraintConversion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
}
