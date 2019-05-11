package org.ternlang.compile.staticanalysis;

public class InvalidGenericCountTest extends CompileTestCase {

   private static final String FAILURE_1 =
   "func fun<A, B>(a: A): B {\n"+
   "  return null;\n"+
   "}\n"+
   "fun<Double, Double, Double>(1.0).intValue();\n";
   
   public void testGenericFunction() throws Exception {
      assertCompileError(FAILURE_1, "Generic parameter count for 'default.fun(a: Object)' is invalid in /default.tern at line 4");
   }
}
