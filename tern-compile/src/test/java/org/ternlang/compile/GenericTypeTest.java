package org.ternlang.compile;

import java.lang.reflect.Type;

import junit.framework.TestCase;

public class GenericTypeTest extends TestCase {

   private static final String SOURCE_1 =
   "class Foo<K, V> {\n"+
   "   new(x){}\n"+
   "   func(){\n"+
   "      return 11;\n"+
   "   }\n"+
   "}\n"+
   "assert 11 == new Foo(1).func();\n";
   
   private static final String SOURCE_2 =
   "const f = (const x: Iterable<String>, const y: (x: String)) -> x.iterator.forEachRemaining(x -> y(x));\n"+
   "f([1,2,3,4], x -> println(x));";
   
   private static final String SOURCE_3 =
   "class Foo<K, V> {\n"+
   "   const key: K;\n"+
   "   const value: V;\n"+
   "\n"+
   "   new(key: K, value: V){\n"+
   "      this.key = key;\n"+
   "      this.value = value;\n"+
   "   }\n"+
   "   getKey(): K{\n"+
   "      return key;\n"+
   "   }\n"+
   "   getValue(): V{\n"+
   "      return value;\n"+
   "   }\n"+
   "}\n"+
   "assert `1` == new Foo(1,2).getKey().toString();\n"+
   "assert `2` == new Foo(1,2).getValue().toString();\n";         

   private static final String SOURCE_4 =
   "class Foo<K: String, V: Integer> {\n"+
   "   const key: K;\n"+
   "   const value: V;\n"+
   "\n"+
   "   new(key: K, value: V){\n"+
   "      this.key = key;\n"+
   "      this.value = value;\n"+
   "   }\n"+
   "   getKey(): K{\n"+
   "      return key;\n"+
   "   }\n"+
   "   getValue(): V{\n"+
   "      return value;\n"+
   "   }\n"+
   "}\n"+
   "assert `23` == new Foo(`123`,2).getKey().substring(1);\n"+
   "assert 2.0 == new Foo(`123`,2).getValue().doubleValue();\n";
   
   private static final String SOURCE_5 =
   "import util.concurrent.ConcurrentHashMap;\n"+
   "import lang.reflect.Method;\n"+
   "\n"+
   "class Foo<K: Method, V: ConcurrentHashMap> {\n"+
   "   const key: K;\n"+
   "   const value: V;\n"+
   "\n"+
   "   new(key: K, value: V){\n"+
   "      this.key = key;\n"+
   "      this.value = value;\n"+
   "   }\n"+
   "   getKey(): K{\n"+
   "      return key;\n"+
   "   }\n"+
   "   getValue(): V{\n"+
   "      return value;\n"+
   "   }\n"+
   "}\n"+
   "var value: ConcurrentHashMap = new ConcurrentHashMap();\n"+
   "var key: Method = ConcurrentHashMap.class.type.getDeclaredMethod(`get`, Object.class);\n"+
   "\n"+
   "value.put(`a`, 1456);\n"+
   "\n"+
   "assert `get` == new Foo(key, value).getKey().getName();\n"+
   "assert 1456 == new Foo(key, value).getValue().get(`a`);\n";
   
   public void testGenericType() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   
   }   
       
   public void testGenericTypeParameter() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   
   }   
   
   public void testGenericTypeWithNoConstraint() throws Exception {
     Compiler compiler = ClassPathCompilerBuilder.createCompiler();
     Executable executable = compiler.compile(SOURCE_3);
     executable.execute();   
   }
   
   public void testGenericTypeWithDefaultImportConstraint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      executable.execute();
    }
   
   public void testGenericTypeWithExplicitImportConstraint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      System.err.println(SOURCE_5);
      executable.execute();
    }
}
