package tern.compile.staticanalysis;

public class ReturnTypeCompileTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "function foo(x){\n"+
   "   return 1;\n"+
   "}\n";
         
   private static final String SUCCESS_2 =
   "function foo(x): Integer{\n"+
   "   return 1;\n"+
   "}\n";
   
   private static final String SUCCESS_3 =
   "function foo(x): Map{\n"+
   "   return x as Map;\n"+
   "}\n";
   
   private static final String SUCCESS_4 =
   "function foo(x): Map{\n"+
   "   return null;\n"+
   "}\n";   
   
   private static final String FAILURE_1 =
   "function foo(x): Map{\n"+
   "   return 1;\n"+
   "}\n";
   
   private static final String FAILURE_2 =
   "function foo(x): Map{\n"+
   "   if(x) {\n"+
   "      return x;\n"+
   "   } else {\n"+
   "      return 1;\n"+
   "   }\n"+
   "}\n";
   
   private static final String FAILURE_3 =
   "function foo(x): Map{\n"+
   "   if(x) {\n"+
   "      return x;\n"+
   "   } else {\n"+
   "      return `some text`;\n"+
   "   }\n"+
   "}\n"; 
   
   private static final String FAILURE_4 =
   "function foo(x: String): Map{\n"+
   "   return x;\n"+
   "}\n";
   
   private static final String FAILURE_5 =
   "var x1 = `text`;\n"+
   "foo(x1);\n"+
   "function foo(x: Map): List{\n"+
   "   return x;\n"+
   "}\n";


   public void testReferenceIndex() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      //assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4);
      assertCompileError(FAILURE_1, "Cast from 'lang.Integer' to 'util.Map' is not possible in /default.tern at line 2");
      assertCompileError(FAILURE_2, "Cast from 'lang.Integer' to 'util.Map' is not possible in /default.tern at line 5");
      assertCompileError(FAILURE_3, "Cast from 'lang.String' to 'util.Map' is not possible in /default.tern at line 5");
      assertCompileError(FAILURE_4, "Cast from 'lang.String' to 'util.Map' is not possible in /default.tern at line 2");
      assertCompileError(FAILURE_5, "Cast from 'util.Map' to 'util.List' is not possible in /default.tern at line 4");
   }
}
