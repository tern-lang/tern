package org.ternlang.compile;

import junit.framework.TestCase;

public class MethodHandleCreateTest extends TestCase {

   private static final String SOURCE_1 = 
   "import util.stream.Collectors;\n" + 
   "var list = [1, 2, 3, 4].stream().map(Foo::new).collect(Collectors.toList());\n"+
   "class Foo{\n"+
   "   const x;\n"+
   "   new(x){\n"+
   "      this.x = x;\n"+
   "   }\n"+
   "   override toString(){\n"+
   "      return `x=${x}`;\n"+
   "   }\n"+
   "}\n"+
   "println(list);\n"+
   "assert list[0].toString() == 'x=1';\n"+
   "assert list[1].toString() == 'x=2';\n"+
   "assert list[2].toString() == 'x=3';\n"+
   "assert list[3].toString() == 'x=4';\n";
   
   private static final String SOURCE_2 = 
   "class Foo{\n"+
   "   const x;\n"+
   "   new(x){\n"+
   "      this.x = x;\n"+
   "   }\n"+
   "   getX(){\n"+
   "      return x;\n"+
   "   }\n"+
   "   override toString(){\n"+
   "      return `x=${x}`;\n"+
   "   }\n"+
   "}\n"+
   "dump(Foo::new, 11);\n"+
   "dump(Foo::new, 'rr');\n"+
   "function dump(func, value){\n"+
   "   const foo = func(value);\n"+
   "   const x = foo.getX();\n"+
   "   println(x);\n"+
   "   println(foo);\n"+
   "   println(foo.x);\n"+
   "}";
 
   
   public void testFunctionReferenceHandleInCollector() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testFunctionReferenceHandleInFunction() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();

   }
}
