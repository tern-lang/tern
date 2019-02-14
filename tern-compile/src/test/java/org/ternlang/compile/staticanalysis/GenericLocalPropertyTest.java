package org.ternlang.compile.staticanalysis;


import junit.framework.TestCase;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;
import org.ternlang.compile.verify.VerifyError;
import org.ternlang.compile.verify.VerifyException;

public class GenericLocalPropertyTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "class Foo<T: Integer>{\n"+
   "   const x: T;\n"+
   "   func(){\n"+
   "      return x.longValue();\n"+
   "   }\n"+
   "}\n";

   private static final String SUCCESS_2 =
   "class Foo<T: List<Integer>>{\n"+
   "   const x: T;\n"+
   "   func(){\n"+
   "      return x.get(1).longValue();\n"+
   "   }\n"+
   "}\n";

   private static final String SUCCESS_3 =
   "class Foo<T: List<Integer>>{\n"+
   "   const x: T;\n"+
   "   func(){\n"+
   "      return x.stream().findFirst().get().longValue();\n"+
   "   }\n"+
   "}\n";

   private static final String SUCCESS_4 =
   "class Foo<T: List<Integer>>{\n"+
   "   const x: T;\n"+
   "   func(){\n"+
   "      return x.stream().findFirst().get().longValue();\n"+
   "   }\n"+
   "}\n";

   private static final String FAILURE_1 =
   "class Foo<T: Integer>{\n"+
   "   const x: T;\n"+
   "   func(){\n"+
   "      return x.toUpperCase();\n"+
   "   }\n"+
   "}\n";

   private static final String FAILURE_2 =
   "class Foo<T: List<Integer>>{\n"+
   "   const x: T;\n"+
   "   func(){\n"+
   "      return x.containsKey(1);\n"+
   "   }\n"+
   "}\n";

   public void testGenericLocalProperty() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4);
      assertCompileError(FAILURE_1, "Function 'toUpperCase()' not found for 'lang.Integer' in /default.tern at line 4");
      assertCompileError(FAILURE_2, "Function 'containsKey(lang.Integer)' not found for 'util.List' in /default.tern at line 4");
   }
}