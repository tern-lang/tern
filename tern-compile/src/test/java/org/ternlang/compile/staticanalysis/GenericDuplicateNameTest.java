package org.ternlang.compile.staticanalysis;

public class GenericDuplicateNameTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "sort<Integer>([1,46,3,2,4,66,3,3,5,6,77], (a, b) -> Integer.compare(a,b));\n"+
   "func sort<T>(l: List<T>, c: (a, b)) {\n"+
   "   return Collections.sort(l, c);\n"+
   "}\n";

   public void testGenericAsync() throws Exception {
      assertCompileAndExecuteSuccess(SUCCESS_1);
   }

}
