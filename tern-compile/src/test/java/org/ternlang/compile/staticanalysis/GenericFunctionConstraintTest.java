package org.ternlang.compile.staticanalysis;

public class GenericFunctionConstraintTest extends CompileTestCase {

   private static final String SOURCE_1 =
   "type Bag<T> = Map<T, T>;\n"+
   "func foo<T: Bag<String>>(a: T) {\n"+
   "   println(a);\n"+
   "}\n"+
   "foo<Map<String, String>>({:});\n";

   private static final String SOURCE_2 =
   "func foo<T>(a: T) {\n"+
   "   println(a);\n"+
   "}\n"+
   "foo<String>('text');\n";

   private static final String FAILURE_1 =
   "func foo<T>(a: T) {\n"+
   "   println(a);\n"+
   "}\n"+
   "foo<Map<String, String>>('text');\n";

   public void testGenericFunction() throws Exception {
      assertCompileSuccess(SOURCE_1);
      assertCompileSuccess(SOURCE_2);
      assertCompileError(FAILURE_1, "Function 'foo(lang.String)' hidden by generics in /default.tern at line 4");

   }
}
