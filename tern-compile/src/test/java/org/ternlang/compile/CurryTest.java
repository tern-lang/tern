package org.ternlang.compile;

import junit.framework.TestCase;

import org.ternlang.core.scope.EmptyModel;

public class CurryTest extends ScriptTestCase {

   private static final String SOURCE_1 =
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
   
   private static final String SOURCE_2 =
   "func main() {\n"+
   "   const t = \"xx\";\n"+
   "   make()(t, 1);\n"+
   "}\n"+
   "func make() {\n"+
   "   return (a, b) -> println(a);\n"+
   "}\n"+
   "main();\n";
   
   private static final String SOURCE_3 =
   "func main(t) {\n"+
   "   make()(t, 1);\n"+
   "}\n"+
   "func make() {\n"+
   "   return (a, b) -> println(a);\n"+
   "}\n"+
   "main(\"xx\");\n";
   
   private static final String SOURCE_4 =
   "func main(const x) {\n"+
   "   make()(x, 1);\n"+
   "}\n"+
   "func make() {\n"+
   "   return (a, b) -> println(a);\n"+
   "}\n"+
   "main(\"xx\");\n";

   public void testCurry() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
      assertScriptExecutes(SOURCE_4);
   }
}
