package org.ternlang.compile;

import junit.framework.TestCase;

import org.ternlang.core.scope.EmptyModel;

public class FunctionTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "var global =1;\n"+
   "\n"+
   "function a(){\n"+
   "   var x=0;\n"+
   "   var y =0;\n"+
   "   var z=0;\n"+
   "   var v=x+y+z+1;\n"+
   "}\n"+
   "\n"+
   "function b(){\n"+
   "   var aa = 0;\n"+
   "   if(aa==0){\n"+
   "      var y=0;\n"+
   "      y++;\n"+
   "   }\n"+
   "}\n"+
   "a();\n"+
   "b();\n";
   
   private static final String SOURCE_2 =
   "let global =1;\n"+
   "\n"+
   "function a(){\n"+
   "   let x=0;\n"+
   "   let y =0;\n"+
   "   let z=0;\n"+
   "   let v=x+y+z+1;\n"+
   "}\n"+
   "\n"+
   "function b(){\n"+
   "   let aa = 0;\n"+
   "   if(aa==0){\n"+
   "      let y=0;\n"+
   "      y++;\n"+
   "   }\n"+
   "}\n"+
   "a();\n"+
   "b();\n";
   
   public void testFunction() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
   }
}
