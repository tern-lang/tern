package org.ternlang.compile;

import org.ternlang.compile.staticanalysis.CompileTestCase;

public class FunctionCallWithArgumentNamesTest extends CompileTestCase {

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
   
   private static final String SUCCESS_3 =
   "class Point {\n"+
   "   const x;\n"+
   "   const y;\n"+
   "\n"+
   "   new(x, y) {\n"+
   "      this.x = x;\n"+
   "      this.y = y;\n"+
   "   }\n"+
   "}\n"+
   "let p = Point(x: 1, y: 33);\n"+
   "\n"+
   "assert p.x == 1;\n"+
   "assert p.y == 33;\n";

   private static final String FAILURE_1 = 
   "foo(b: 1, 2, 3);\n"+
   "\n"+
   "func foo(a, b, c) {\n"+
   "   println(`${a},${b},${c}`);\n"+
   "}\n";
   
   private static final String FAILURE_2 = 
   "class Foo {\n"+
   "   blah(x, y) {}\n"+
   "}\n"+
   "new Foo().blah(y: 1, x: 2);\n";
   
   private static final String FAILURE_3 =
   "class Point {\n"+
   "   const x;\n"+
   "   const y;\n"+
   "\n"+
   "   new(x, y) {\n"+
   "      this.x = x;\n"+
   "      this.y = y;\n"+
   "   }\n"+
   "}\n"+
   "let p = Point(y: 1, x: 33);\n"+
   "println(p);\n";
   
   public void testArgumentNames() throws Exception {
      assertCompileAndExecuteSuccess(SUCCESS_1);
      assertCompileAndExecuteSuccess(SUCCESS_2);
      assertCompileAndExecuteSuccess(SUCCESS_3);
      assertCompileError(FAILURE_1, "Function 'foo(lang.Integer, lang.Integer, lang.Integer)' has an invalid argument name in /default.tern at line 1");
      assertCompileError(FAILURE_2, "Function 'blah(lang.Integer, lang.Integer)' has an invalid argument name in /default.tern at line 4");
      assertCompileError(FAILURE_3, "Constructor 'new(lang.Integer, lang.Integer)' has an invalid argument name in /default.tern at line 10");
   }
  
}
