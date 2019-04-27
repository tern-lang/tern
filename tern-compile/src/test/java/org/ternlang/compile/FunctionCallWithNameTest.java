package org.ternlang.compile;

import org.ternlang.compile.staticanalysis.CompileTestCase;

public class FunctionCallWithNameTest extends CompileTestCase {

   private static final String SUCCESS_1 = 
   "foo(a: 1, b: 2, c: 3);\n"+
   "\n"+
   "func foo(a, b, c) {\n"+
   "   println(`${a},${b},${c}`);\n"+
   "}\n";
   
   private static final String SUCCESS_2 = 
   "foo(a: 1, 2, 3);\n"+
   "\n"+
   "func foo(a, b, c) {\n"+
   "   println(`${a},${b},${c}`);\n"+
   "}\n";
   
   public void testArgumentNames() throws Exception {
      assertCompileAndExecuteSuccess(SUCCESS_1);
      assertCompileAndExecuteSuccess(SUCCESS_2);
   }
  
}
