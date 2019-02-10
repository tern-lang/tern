package tern.compile;

import junit.framework.TestCase;

import tern.core.scope.EmptyModel;

public class CurryTest extends TestCase {

   private static final String SOURCE =
   "module Curry {\n"+
   "   class Foo{\n"+
   "      var x;\n"+
   "      new(x){\n"+
   "         this.x=x;\n"+
   "      }\n"+
   "   }\n"+
   "   foo(x){\n"+
   "      return new Foo(x);\n"+
   "   }\n"+
   "   func(x){\n"+
   "      return (y) -> x+y;\n"+
   "   }\n"+
   "   func2(x){\n"+
   "      return [(y) -> x+y];\n"+
   "   }\n"+   
   "}\n"+
   "var f = Curry.func(1);\n"+
   "var l = [f];\n"+
   "var t = Curry.foo('a');\n"+
   "assert f(5) == 6;\n"+
   "assert l[0](3) == 4;\n"+
   "assert Curry.func(1)(2) == 3;\n"+
   "assert Curry.func2(1)[0](2) == 3;\n";

   
   public void testCurry() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
