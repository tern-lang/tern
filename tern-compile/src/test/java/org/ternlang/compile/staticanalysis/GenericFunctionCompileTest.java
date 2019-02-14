package org.ternlang.compile.staticanalysis;

import org.ternlang.core.Bug;

public class GenericFunctionCompileTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "var m: Map<String, Integer> = {'x':1};\n"+
   "m.get('x').longValue();\n";
   
   private static final String SUCCESS_2 =
   "var m: Map<String, String> = {'x':'x'};\n"+
   "m.get('x').substring(1);\n";   
   
   private static final String SUCCESS_3 =
   "var l: List<String> = ['x'];\n"+
   "l.get(0).substring(1);\n"; 
   
   private static final String SUCCESS_4 =
   "var l: List<Integer> = [1];\n"+
   "l.get(0).doubleValue();\n";    
   
   private static final String SUCCESS_5 =
   "var l: List = [1];\n"+
   "l.get(0).doubleValue();\n";  
   
   private static final String SUCCESS_6 =
   "var m: Map = {'x':'x'};\n"+
   "m.get('x').substring(1);\n";     
         
   private static final String SUCCESS_7 =
   "class Foo{}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<T> extends Blah<T, T>{}\n"+
   "var n: Nuh<String> = new Nuh<String>();\n"+
   "n.funA(1).substring(1);\n";   
   
   private static final String SUCCESS_8 =
   "class Foo{\n"+
   "  func(){}\n"+
   "}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<K> extends Blah<Foo, K>{}\n"+
   "var n: Nuh<String> = new Nuh<String>();\n"+
   "n.funA(1).func();\n";    
   
   private static final String SUCCESS_9 =
   "class Foo{\n"+
   "  func(){}\n"+
   "}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<K> extends Blah<K, K>{}\n"+
   "var n: Nuh<Foo> = new Nuh<Foo>();\n"+
   "n.funA(1).func();\n";  
   
   private static final String SUCCESS_10 =
   "class Foo{\n"+
   "  func(){}\n"+
   "}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<K> extends Blah<K, K>{}\n"+
   "var n: Nuh = new Nuh();\n"+
   "n.funA(1).func();\n";   
   
   private static final String SUCCESS_11 =
   "class One<A>{\n"+
   "   func():A{\n"+
   "   }\n"+
   "   boo():Map<String, A>{\n"+
   "   }\n"+
   "   basic():String{\n"+
   "   }\n"+
   "}\n"+
   "class Two<A, B> extends One<List<B>>{\n"+
   "   blah():A{\n"+
   "   }\n"+
   "}\n"+
   "class Three extends Two<Integer, List<Integer>>{\n"+
   "}\n"+
   "var two: Two<Integer, Map<Boolean, String>> = new Two<Integer, Map<Boolean, String>>();\n"+
   "two.func().get(0).get(true).substring(1);\n"+
   "two.boo().get('x').get(1).get(true).substring(1);\n";   
   
   private static final String SUCCESS_12 =
   "class One<A>{\n"+
   "   func():A{\n"+
   "   }\n"+
   "   boo():Map<String, A>{\n"+
   "   }\n"+
   "   basic():String{\n"+
   "   }\n"+
   "}\n"+
   "class Two<A, B> extends One<List<B>>{\n"+
   "   blah():A{\n"+
   "   }\n"+
   "}\n"+
   "class Three extends Two<Integer, List<Integer>>{\n"+
   "}\n"+
   "var two: Two<Integer, Map<Boolean, String>> = new Two<Integer, Map<Boolean, String>>();\n"+
   "two.basic().substring(1);\n";

   private static final String SUCCESS_13 =
   "func foo<T>(a: T) {\n"+
   "   println(a.class);\n"+
   "}\n"+
   "foo<Integer>(1);\n"+
   "foo<String>('a');\n";

   private static final String SUCCESS_14 =
   "class Bar{\n"+
   "   foo<T>(a: T) {\n"+
   "      println(a.class);\n"+
   "   }\n"+
   "}\n"+
   "let b = new Bar();\n"+
   "\n"+
   "b.foo<Integer>(1);\n"+
   "b.foo<String>('a');\n";

   private static final String SUCCESS_15 =
   "type Num = Number;\n"+
   "func fun<T: Num>(n: T): T {\n"+
   "   return n;\n"+
   "}\n"+
   "fun(1);\n";

   private static final String FAILURE_1 =
   "var m: Map<String, String> = {'x':'x'};\n"+
   "m.get('x').longValue();\n";
   
   private static final String FAILURE_2 =
   "var m: Map<String, Integer> = {'x':1};\n"+
   "m.get('x').substring(1);\n";      
   
   private static final String FAILURE_3 =
   "class Foo{}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<T> extends Blah<T, T>{}\n"+
   "var n: Nuh<String> = new Nuh<String>();\n"+
   "n.funA(1).longValue();\n";     
   
   private static final String FAILURE_4 =
   "class Foo{\n"+
   "  func(){}\n"+
   "}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<K> extends Blah<K, Foo>{}\n"+
   "var n: Nuh<String> = new Nuh<String>();\n"+
   "n.funB(1).func();\n";   
   
   private static final String FAILURE_5 =
   "class One<A>{\n"+
   "   func():A{\n"+
   "   }\n"+
   "   boo():Map<String, A>{\n"+
   "   }\n"+
   "   basic():String{\n"+
   "   }\n"+
   "}\n"+
   "class Two<A, B> extends One<List<B>>{\n"+
   "   blah():A{\n"+
   "   }\n"+
   "}\n"+
   "class Three extends Two<Integer, List<Integer>>{\n"+
   "}\n"+
   "var two: Two<Integer, Map<Boolean, String>> = new Two<Integer, Map<Boolean, String>>();\n"+
   "two.func().get(0).get(true).longValue();\n";
   
   private static final String FAILURE_6 =
   "class One<A>{\n"+
   "   func():A{\n"+
   "   }\n"+
   "   boo():Map<String, A>{\n"+
   "   }\n"+
   "   basic():String{\n"+
   "   }\n"+
   "}\n"+
   "class Two<A, B> extends One<List<B>>{\n"+
   "   blah():A{\n"+
   "   }\n"+
   "}\n"+
   "class Three extends Two<Integer, List<Integer>>{\n"+
   "}\n"+
   "var two: Two<Integer, Map<Boolean, String>> = new Two<Integer, Map<Boolean, String>>();\n"+
   "two.func().get(true).get(true);\n";
   
   private static final String FAILURE_7 =
   "var l: List<Map<?,String>>=new ArrayList<Map<?,String>>();\n"+
   "l.get(1).get('x').longValue();\n";            

   private static final String FAILURE_8 =
   "var l: List<Map<?,String>>=new ArrayList<Map<?,String>>();\n"+
   "l.get(1).values().iterator().next().longValue();\n";  
   
   private static final String FAILURE_9 =
   "var l: List<Map<?,String>>=new ArrayList<Map<?,String>>();\n"+
   "l.get(1).entrySet().iterator().next().getValue().longValue();\n";

   public void testFunctionGenerics() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4);
      assertCompileSuccess(SUCCESS_5);
      assertCompileSuccess(SUCCESS_6);
      assertCompileSuccess(SUCCESS_7);
      assertCompileSuccess(SUCCESS_8);
      assertCompileSuccess(SUCCESS_9);
      assertCompileSuccess(SUCCESS_10);
      assertCompileSuccess(SUCCESS_11);
      assertCompileSuccess(SUCCESS_12);
      assertCompileSuccess(SUCCESS_13);
      assertCompileSuccess(SUCCESS_14);
      assertCompileSuccess(SUCCESS_15);
      assertCompileError(FAILURE_1, "Function 'longValue()' not found for 'lang.String' in /default.tern at line 2");
      assertCompileError(FAILURE_2, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.tern at line 2");
      assertCompileError(FAILURE_3, "Function 'longValue()' not found for 'lang.String' in /default.tern at line 11");
      assertCompileError(FAILURE_4, "Function 'func()' not found for 'lang.String' in /default.tern at line 13");
      assertCompileError(FAILURE_5, "Function 'longValue()' not found for 'lang.String' in /default.tern at line 16");
      assertCompileError(FAILURE_6, "Function 'get(lang.Boolean)' not found for 'util.List' in /default.tern at line 16");
      assertCompileError(FAILURE_7, "Function 'longValue()' not found for 'lang.String' in /default.tern at line 2");
      assertCompileError(FAILURE_8, "Function 'longValue()' not found for 'lang.String' in /default.tern at line 2");
      assertCompileError(FAILURE_9, "Function 'longValue()' not found for 'lang.String' in /default.tern at line 2");
   }
}
