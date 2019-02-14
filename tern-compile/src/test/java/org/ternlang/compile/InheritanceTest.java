package org.ternlang.compile;

import junit.framework.TestCase;

public class InheritanceTest extends TestCase {

   private static final String SOURCE_1 =
   "class X {\n"+
   "   trait InX{\n"+
   "      dump(x,y);\n"+
   "   }\n"+
   "}\n"+
   "class Y extends X {\n"+
   "}\n"+
   "class Z with X.InX{\n"+
   "   dump(x,y){\n"+
   "      println(\"${x},${y}\");\n"+
   "   }\n"+
   "}\n"+
   "var types = X.class.getTypes();\n"+
   "println(types);\n"+
   "println(X.class);\n"+
   "println(Y.class);\n"+
   "println(X.class.getTypes());\n"+
   "println(Y.class.getTypes());\n"+
   "println(Z.class.getTypes());\n"+
   "var z = new Z();\n"+
   "z.dump(1,2);\n";
   
   private static final String SOURCE_2 =
   "class Base {\n"+
   "   trait Trait{\n"+
   "      const X='Base';\n"+
   "      abstract foo(x,y);\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class Next extends Base {\n"+
   "\n"+
   "   @SomeAnnotation(a: 1, b: 2)\n"+
   "   class Blah with Trait{\n"+
   "      override foo(x,y){\n"+
   "         println(\"x=${x} y=${y}\");\n"+
   "      }\n"+
   "   }\n"+
   "\n"+
   "   create() :Trait{\n"+
   "      return new Blah();\n"+
   "   }\n"+
   "}\n"+
   "class X with Base.Trait {\n"+
   "   override foo(x,y){\n"+
   "      println(\"x=${x} y=${y}\");\n"+
   "   }\n"+
   "}\n"+
   "var v = new Next();\n"+
   "var i = v.create();\n"+
   "assert i instanceof Base.Trait;\n";
         
   // Outer$Next$Trait 
   // Outer$Trait // parent
   // Base$Trait // parent-base
   
   public void testSimpleHierarchy() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testHierarchy() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
}
