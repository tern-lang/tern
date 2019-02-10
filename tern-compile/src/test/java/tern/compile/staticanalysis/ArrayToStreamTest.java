package tern.compile.staticanalysis;

public class ArrayToStreamTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "var x: String[] = ['x', 'y', 'z'];\n"+
   "var v = x.stream().findFirst().get().toUpperCase();\n"+
   "\n"+
   "assert v == 'X';\n";

   private static final String SUCCESS_2 =
   "import util.stream.Collectors;\n"+
   "\n"+
   "var x: String[] = ['x', 'y', 'z'];\n"+
   "var v = x.stream()\n"+
   "    .map(e -> e.toUpperCase())\n"+
   "    .collect(Collectors.toList())\n"+
   "    .stream()\n"+
   "    .findFirst()\n"+
   "    .get()\n"+
   "    .toUpperCase();\n"+
   "\n"+
   "assert v == 'X';\n";

   private static final String SUCCESS_3 =
   "var x: Integer[] = [1,2,3];\n"+
   "var v = x.stream()\n"+
   "    .findFirst()\n"+
   "    .get()\n"+
   "    .longValue();\n"+
   "\n"+
   "assert v == 1L;\n";

   private static final String SUCCESS_4 =
   "var x: Integer[] = [1,2,3];\n"+
   "x.forEach(e -> println(e));\n";

   private static final String SUCCESS_5 =
   "var x: Integer[] = [1,2,3];\n"+
   "x.iterator().forEachRemaining(e -> println(e));\n";

   private static final String SUCCESS_6 =
   "var x: Integer[] = [1,2,3];\n"+
   "x.iterator().next().longValue();\n";

   private static final String SUCCESS_7 =
   "var x: Integer[] = [1,2,3];\n"+
   "x.subList(0,2).get(0).longValue();\n";

   private static final String FAILURE_1 =
   "var x: String[] = ['x', 'y', 'z'];\n"+
   "var v = x.stream().findFirst().get().longValue();\n"+
   "\n"+
   "assert v == 'X';\n";

   private static final String FAILURE_2 =
   "var x: Integer[] = [1,2,3];\n"+
   "var v = x.stream()\n"+
   "    .findFirst()\n"+
   "    .get()\n"+
   "    .toUpperCase();\n"+
   "\n"+
   "assert v == 1L;\n";

   private static final String FAILURE_3 =
   "var x: Integer[] = [1,2,3];\n"+
   "x.iterator().next().toUpperCase();\n";

   private static final String FAILURE_4 =
   "var x: Integer[] = [1,2,3];\n"+
   "x.subList(0,2).get(0).substring(1);\n";

   
   public void testArrayToStream() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4);
      assertCompileSuccess(SUCCESS_5);
      assertCompileSuccess(SUCCESS_6);
      assertCompileSuccess(SUCCESS_7);
      assertCompileError(FAILURE_1, "Function 'longValue()' not found for 'lang.String' in /default.snap at line 2");
      assertCompileError(FAILURE_2, "Function 'toUpperCase()' not found for 'lang.Integer' in /default.snap at line 5");
      assertCompileError(FAILURE_3, "Function 'toUpperCase()' not found for 'lang.Integer' in /default.snap at line 2");
      assertCompileError(FAILURE_4, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.snap at line 2");
   }
}