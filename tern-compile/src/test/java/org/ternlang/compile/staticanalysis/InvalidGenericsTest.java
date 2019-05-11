package org.ternlang.compile.staticanalysis;

public class InvalidGenericsTest extends CompileTestCase {
   
   private static final String FAILURE_1 = 
   "class Foo<T>{\n"+
   "   dump(a): T {}\n"+
   "}\n"+
   "new Foo<Double>().dump(11).size();\n"; 
   
   private static final String FAILURE_2 = 
   "class Foo<T>{\n"+
   "   dump(a): T {}\n"+
   "}\n"+
   "Foo<Double>().dump(11).size();\n";    
   
   private static final String FAILURE_3 =
   "func fun<A, B>(a: A): List<B> {\n"+
   "  return null;\n"+
   "}\n"+
   "fun<Double, Double, Double>(1.0).intValue();\n";
   
   public void testGenericFunction() throws Exception {
      assertCompileError(FAILURE_1, "Function 'size()' not found for 'lang.Double' in /default.tern at line 4");
      assertCompileError(FAILURE_2, "Function 'size()' not found for 'lang.Double' in /default.tern at line 4");
      assertCompileError(FAILURE_3, "Generic parameter count for 'default.fun(a: Object)' is invalid in /default.tern at line 4");

   }
}
