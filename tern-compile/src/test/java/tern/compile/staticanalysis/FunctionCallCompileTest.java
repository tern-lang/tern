package tern.compile.staticanalysis;


public class FunctionCallCompileTest extends CompileTestCase {

   public static final String FAILURE_1 = 
   "function foo(x: Integer){\n"+
   "   return x.toString();\n"+
   "}\n"+
   "var x1: Integer[] = [1,2,3];\n"+
   "foo(x1);\n";
   
   public static final String FAILURE_2 =
   "class Blah{\n"+
   "   go(x: Integer, y: Integer){\n"+
   "      return x.toString();\n"+
   "   }\n"+
   "}\n"+
   "var x: Integer[] = [1,2,3];\n"+
   "new Blah().go(x);\n";         

   public static final String FAILURE_3 =
   "class Blah{\n"+
   "   go(x: Integer[]){\n"+
   "      return x.toString();\n"+
   "   }\n"+
   "}\n"+
   "println(Integer[].class.getTypes());\n"+
   "var x: Integer[][] = [[1,2,3],[1]];\n"+
   "new Blah().go(x);\n";

   
   public void testFunctionCallCompilation() throws Exception {
      assertCompileError(FAILURE_1, "Function 'foo(lang.Integer[])' not found in scope in /default.snap at line 5");
      assertCompileError(FAILURE_2, "Function 'go(lang.Integer[])' not found for 'default.Blah' in /default.snap at line 7");
      assertCompileError(FAILURE_3, "Function 'go(lang.Integer[][])' not found for 'default.Blah' in /default.snap at line 8");        
   }
}
