package tern.compile.staticanalysis;

public class GenericFunctionTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "func fun<T: Number>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun<Double>(1.0).intValue();\n";
   
   private static final String SUCCESS_2 =
   "class Foo<K, V>{\n"+
   "   fun<T: Integer>(a: K, b: V): List<T> {\n"+
   "      return [1,2,3];\n"+
   "   }\n"+
   "}\n"+
   "new Foo<String, Double>().fun('a', 1.1).get(0).intValue();\n";
   
   private static final String SUCCESS_3 =
   "func fun<T>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun<Double>(1.0).intValue();\n";
   
   private static final String SUCCESS_4 =
   "func fun<T>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun(1.0).intValue();\n"; // no qualification at all
   
   private static final String SUCCESS_5 =
   "func fun<F, B>(b: B): List<F> {\n"+
   "   return [['a','b']];\n"+
   "}\n"+
   "let u = fun<List<String>, Number>(11).get(0).get(0).toUpperCase();\n"+
   "println(u);\n";    
   
   private static final String SUCCESS_6 =
   "let x = <T: Runnable>(a: T) -> a.run();\n"+
   "println(x);\n";

   private static final String SUCCESS_7 =
   "func test<T: List<?>>(a: T, b: T): T {\n"+
   "   return [];\n"+
   "}\n"+
   "test([], []);\n";

   private static final String SUCCESS_8 =
   "module Mod {\n"+
    "   fib<T: Number>(n: T): Iterable<T> {\n"+
    "      return [n];\n"+
    "   }\n"+
    "}\n";

   private static final String SUCCESS_9 =
   "class Typ {\n"+
   "   fib<T: Number>(n: T): Iterable<T> {\n"+
   "      return [n];\n"+
   "   }\n"+
   "}\n";

   private static final String FAILURE_1 =
   "func fun<T: Number>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun<Double>(1.0).substring(1);\n";
   
   private static final String FAILURE_2 =
   "class Foo<K, V>{\n"+
   "   fun<T: Integer>(a: K, b: V): List<T> {\n"+
   "      return [1,2,3];\n"+
   "   }\n"+
   "}\n"+
   "new Foo<String, Double>().fun('a', 1.1).get(0).substring(1);\n";
   
   private static final String FAILURE_3 =
   "func fun<T: Integer>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun(1.0).substring(1);\n"; // no qualifier so default
   
   
   private static final String FAILURE_4 =
   "func fun<F, B>(b: B): List<F> {\n"+
   "   return [['a','b']];\n"+
   "}\n"+
   "let u = fun<List<String>, Number>(11).get(0).get(0).intValue();\n";

   private static final String FAILURE_5 =
   "func go<T: Runnable>(a: T){\n"+
   "   a.run();\n"+
   "}\n"+
   "go<String>(()->println('x'));\n";

   public void testGenericFunction() throws Exception {
      assertCompileAndExecuteSuccess(SUCCESS_1);
      assertCompileAndExecuteSuccess(SUCCESS_2);
      assertCompileAndExecuteSuccess(SUCCESS_3);
      assertCompileAndExecuteSuccess(SUCCESS_4);
      assertCompileAndExecuteSuccess(SUCCESS_5);
      assertCompileAndExecuteSuccess(SUCCESS_6);
      assertCompileAndExecuteSuccess(SUCCESS_7);
      assertCompileAndExecuteSuccess(SUCCESS_8);
      assertCompileAndExecuteSuccess(SUCCESS_9);
      assertCompileError(FAILURE_1, "Function 'substring(lang.Integer)' not found for 'lang.Double' in /default.snap at line 4");
      assertCompileError(FAILURE_2, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.snap at line 6");
      assertCompileError(FAILURE_3, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.snap at line 4");
      assertCompileError(FAILURE_4, "Function 'intValue()' not found for 'lang.String' in /default.snap at line 4");
      assertCompileError(FAILURE_5, "Generic parameter 'T' does not match 'lang.Runnable' in /default.snap at line 4");
   }
}
