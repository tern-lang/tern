package org.ternlang.compile;


public class ModuleEvalTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "module Shape{\n"+
   "   class Point{\n"+
   "      var x,y;\n"+
   "      new(x,y){\n"+
   "         this.x=x;\n"+
   "         this.y=y;\n"+
   "      }\n"+
   "      toString(){\n"+
   "         \"${x},${y}\";\n"+
   "      }\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_2 =
   "assert eval('new Shape.Point(1,2)', 'example').x == 1;\n";

   public void testModuleEval() throws Exception {
      addScript("/example/Shape.tern", SOURCE_1);
      addScript("/run.tern", SOURCE_2);
      assertScriptExecutes("/run.tern");
   }
   
}
