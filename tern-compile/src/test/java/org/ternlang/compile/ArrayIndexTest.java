package org.ternlang.compile;

import org.ternlang.compile.staticanalysis.CompileTestCase;
import org.ternlang.core.Bug;

public class ArrayIndexTest extends CompileTestCase {

   private final static String SOURCE_1 =
   "class Point{\n"+
   "   var x;\n"+
   "   var y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "}\n"+
   "var arr = new Point[10];\n"+
   "for(var i in 0..2){\n"+
   "   arr[2]=new Point(2,4);\n"+
   "}\n";
   
   private static final String SOURCE_2 = 
   "func swap<T: Number>(a: T, b: T): List<T> {\n"+
   "   return [b, a];\n"+
   "}\n"+
   "\n"+
   "let l = swap<Float>(2, 3).get(0).doubleValue();\n"+
   "println(l);\n";

   private static final String FAILURE_1 = 
   "func swap<T: Number>(a: T, b: T): List<T> {\n"+
   "   return [b, a];\n"+
   "}\n"+
   "\n"+
   "let l = swap<Float>(2, 3).get(0).doubleVal();\n"+
   "println(l);\n";     
   
   private static final String FAILURE_2 = 
   "func swap<T: Number>(a: T, b: T): List<T> {\n"+
   "   return [b, a];\n"+
   "}\n"+
   "\n"+
   "let l = swap<Float>(2, 3)[0].doubleVal();\n"+ // this should be a compile error
   "println(l);\n";   
   
   @Bug
   public void testIndex() throws Exception {
      assertCompileAndExecuteSuccess(SOURCE_1);
      assertCompileSuccess(SOURCE_2);
      assertCompileError(FAILURE_1, "Function 'doubleVal()' not found for 'lang.Float' in /default.tern at line 5");
      //assertCompileError(FAILURE_2, "Function 'doubleVal()' not found for 'lang.Float' in /default.tern at line 5");
   }
}
