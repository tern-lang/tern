package org.ternlang.compile;

import junit.framework.TestCase;

public class CoercionOfAtomicNumbersTest extends TestCase {

   private static final String SOURCE =
   "import util.concurrent.atomic.AtomicInteger;\n"+
   "trait Foo extends Iterator {\n"+
   "   override next(){\n"+
   "      return value().getAndIncrement();\n"+
   "   }\n"+
   "   override hasNext(){\n"+
   "      return value().get() < 100;\n"+
   "   }\n"+
   "   abstract value(): AtomicInteger;\n"+
   "   func() {\n"+
   "      println(\"func()\");\n"+
   "   }\n"+
   "}\n"+
   "class FooImpl with Foo {\n"+
   "   var i = new AtomicInteger();\n"+
   "   override value(): AtomicInteger{\n"+ // this should not be coerced or modified
   "      return i;\n"+
   "   }\n"+
   "}\n"+
   "var foo = new FooImpl();\n"+
   "while(foo.hasNext()){\n"+
   "   foo.func();\n"+
   "   println(foo.next());\n"+
   "}\n";
   
   public void testTraitExtendsInterface() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
