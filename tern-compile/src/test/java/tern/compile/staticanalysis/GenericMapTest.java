package tern.compile.staticanalysis;


import junit.framework.TestCase;

import tern.compile.ClassPathCompilerBuilder;
import tern.compile.Compiler;
import tern.compile.verify.VerifyError;
import tern.compile.verify.VerifyException;

public class GenericMapTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "var x: Map<String, String> = {:};\n"+
   "x.entrySet().stream().findFirst().get().getValue().toUpperCase();\n";

   private static final String SUCCESS_2 =
   "var x: Map<String, String> = {:};\n"+
   "x.values().stream().findFirst().get().toUpperCase();\n";

   private static final String SUCCESS_3 =
   "var x: Map<String, String[]> = {:};\n"+
   "x.values()\n"+
   "   .stream()\n"+
   "   .findFirst()\n"+
   "   .get()\n"+
   "   .stream()\n"+
   "   .findFirst()\n"+
   "   .get()\n"+
   "   .toUpperCase();\n";

   private static final String FAILURE_1 =
   "var x: Map<String, String> = {:};\n"+
   "x.entrySet().stream().findFirst().get().getValue().longValue();\n";

   private static final String FAILURE_2 =
   "var x: Map<String, String> = {:};\n"+
   "x.values().stream().findFirst().get().longValue();\n";

   private static final String FAILURE_3 =
   "var x: Map<String, String[]> = {:};\n"+
   "x.values()\n"+
   "   .stream()\n"+
   "   .findFirst()\n"+
   "   .get()\n"+
   "   .stream()\n"+
   "   .findFirst()\n"+
   "   .get()\n"+
   "   .longValue();\n";

   
   public void testGenericMap() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileError(FAILURE_1, "Function 'longValue()' not found for 'lang.String' in /default.tern at line 2");
      assertCompileError(FAILURE_2, "Function 'longValue()' not found for 'lang.String' in /default.tern at line 2");
      assertCompileError(FAILURE_3, "Function 'longValue()' not found for 'lang.String' in /default.tern at line 9");
   }
}