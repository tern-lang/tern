package tern.compile.staticanalysis;

public class AliasDefinitionTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "type Foo = Map<String, String>;\n"+
   "let x: Foo = {:};\n"+
   "x.get('a').substring(1);\n";   
   
   private static final String SUCCESS_2 =
   "module Mod {\n"+
   "   type Foo = Map<String, String>;\n"+
   "\n"+
   "   fun(): Foo {\n"+
   "      return {:};\n"+
   "   }\n"+
   "}\n"+
   "Mod.fun().get('a').substring(1);\n";
   
   private static final String SUCCESS_3 =
   "module Mod {\n"+
   "   type Foo = Map<String, Double>;\n"+
   "\n"+
   "   fun(): Foo {\n"+
   "      return {:};\n"+
   "   }\n"+
   "}\n"+
   "Mod.fun().get('a').intValue();\n";
   
   private static final String SUCCESS_4 =
   "class Typ {\n"+
   "   type Foo = Map<String, String>;\n"+
   "\n"+
   "   fun(): Foo {\n"+
   "      return {:};\n"+
   "   }\n"+
   "}\n"+
   "new Typ().fun().get('a').substring(1);\n";

   private static final String SUCCESS_5 =
   "type Foo<T> = Map<T, String>;\n"+
   "\n"+
   "func foo(): Foo<Integer> {\n"+
   "   return {'a':'a'};\n"+
   "}\n"+
   "\n"+
   "foo().keySet().iterator().next().intValue();\n";

   private static final String SUCCESS_6 =
   "type Foo<T> = Map<T, String>;\n"+
   "type Blah<S> = Foo<S>;\n"+
   "\n"+
   "class Typ<R> {\n"+
   "\n"+
   "   foo(): Blah<R> {\n"+
   "      return {'a':'a'};\n"+
   "   }\n"+
   "}\n"+
   "let x: Typ<String> = new Typ<String>();\n"+
   "x.foo().keySet().iterator().next().substring(1);\n";

   private static final String SUCCESS_7 =
   "type Foo<T> = Map<T, String>;\n"+
   "type Blah<S> = Foo<S>;\n"+
   "\n"+
   "class Typ<R> {\n"+
   "\n"+
   "   foo(): Blah<R> {\n"+
   "      return {1:'a'};\n"+
   "   }\n"+
   "}\n"+
   "let x: Typ<Integer> = new Typ<Integer>();\n"+
   "x.foo().keySet().iterator().next().intValue();\n";

   private static final String SUCCESS_8 =
   "type Num = Double;\n"+
   "let x: Num = 1;\n"+
   "println(--x);\n";

   private static final String FAILURE_1 =
   "type Foo = Map<String, String>;\n"+
   "let x: Foo = {:};\n"+
   "x.get('a').intValue();\n";
   
   private static final String FAILURE_2 =
   "module Mod {\n"+
   "   type Foo = Map<String, String>;\n"+
   "\n"+
   "   fun(): Foo {\n"+
   "      return {:};\n"+
   "   }\n"+
   "}\n"+
   "Mod.fun().get('a').intValue();\n";

   private static final String FAILURE_3 =
   "type Foo<T> = Map<T, String>;\n"+
    "\n"+
    "func foo(): Foo<Integer> {\n"+
    "   return {'a':'a'};\n"+
    "}\n"+
    "\n"+
    "foo().keySet().iterator().next().substring(1);\n";

   private static final String FAILURE_4 =
   "type Foo<T> = Map<T, String>;\n"+
   "type Blah<S> = Foo<S>;\n"+
   "\n"+
   "class Typ<R> {\n"+
   "\n"+
   "   foo(): Blah<R> {\n"+
   "      return {1:'a'};\n"+
   "   }\n"+
   "}\n"+
   "let x: Typ<Integer> = new Typ<Integer>();\n"+
   "x.foo().keySet().iterator().next().substring(1);\n";

   public void testGenericFunction() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4);
      assertCompileSuccess(SUCCESS_5);
      assertCompileSuccess(SUCCESS_6);
      assertCompileSuccess(SUCCESS_7);
      assertCompileSuccess(SUCCESS_8);
      assertCompileError(FAILURE_1, "Function 'intValue()' not found for 'lang.String' in /default.snap at line 3");
      assertCompileError(FAILURE_2, "Function 'intValue()' not found for 'lang.String' in /default.snap at line 8");
      assertCompileError(FAILURE_3, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.snap at line 7");
      assertCompileError(FAILURE_4, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.snap at line 11");
   }
}