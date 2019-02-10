package tern.compile.staticanalysis;


import junit.framework.TestCase;

import tern.compile.ClassPathCompilerBuilder;
import tern.compile.Compiler;
import tern.compile.verify.VerifyError;
import tern.compile.verify.VerifyException;

public class GenericPropertyTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "var f: Foo<String> = new Foo<String>();\n"+
   "f.x.substring(1);\n";

   private static final String SUCCESS_2 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "var f: Foo<List<String>> = new Foo<List<String>>();\n"+
   "f.x.get(0).substring(1);\n";

   private static final String SUCCESS_3 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "class Bar<A: String, B> extends Foo<B>{\n"+
   "}\n"+
   "var b: Bar<String, List<String>> = new Bar<String, List<String>>();\n"+
   "b.x.get(0).toUpperCase();\n";

   private static final String SUCCESS_4 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "class Bar<A, B> extends Foo<B>{\n"+
   "}\n"+
   "var b: Bar<String, ?> = new Bar<String, ?>();\n"+
   "b.x.get(0).longValue();\n";

   private static final String SUCCESS_5 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "class Bar<A, B> extends Foo<B>{\n"+
   "}\n"+
   "var b: Bar<String, ?> = new Bar<String, ?>();\n"+
   "b.x.get(0).toUpperCase();\n";

   private static final String SUCCESS_6 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "class Bar<A, B> extends Foo<B>{\n"+
   "}\n"+
   "var b: Bar<String, String[]> = new Bar<String, String[]>();\n"+
   "b.x.stream().findFirst().get().toUpperCase();\n";

   private static final String FAILURE_1 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "var f: Foo<String> = new Foo<String>();\n"+
   "f.x.longValue();\n";

   private static final String FAILURE_2 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "var f: Foo<List<String>> = new Foo<List<String>>();\n"+
   "f.x.get(0).longValue();\n";

   private static final String FAILURE_3 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "class Bar<A: String, B> extends Foo<B>{\n"+
   "}\n"+
   "var b: Bar<String, List<String>> = new Bar<String, List<String>>();\n"+
   "b.x.get(0).longValue();\n";

   private static final String FAILURE_4 =
   "class Foo<T>{\n"+
   "   var x: T;\n"+
   "}\n"+
   "class Bar<A, B> extends Foo<B>{\n"+
   "}\n"+
   "var b: Bar<String, String[]> = new Bar<String, String[]>();\n"+
   "b.x.stream().findFirst().get().longValue();\n";

   
   public void testGenericProperty() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4);
      assertCompileSuccess(SUCCESS_5);
      assertCompileSuccess(SUCCESS_6);
      assertCompileError(FAILURE_1, "Function 'longValue()' not found for 'lang.String' in /default.snap at line 5");
      assertCompileError(FAILURE_2, "Function 'longValue()' not found for 'lang.String' in /default.snap at line 5");
      assertCompileError(FAILURE_3, "Function 'longValue()' not found for 'lang.String' in /default.snap at line 7");
      assertCompileError(FAILURE_4, "Function 'longValue()' not found for 'lang.String' in /default.snap at line 7");
   }
}