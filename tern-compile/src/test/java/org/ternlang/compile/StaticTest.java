package org.ternlang.compile;

import org.ternlang.compile.staticanalysis.CompileTestCase;

public class StaticTest extends CompileTestCase{
   
   private static final String SOURCE_1 = 
   "class Foo{\n"+
   "   static inc(){\n"+
   "      return 1;\n"+
   "   }\n"+
   "}\n"+
   "Foo.inc();\n";
   
   private static final String SOURCE_2 =
   "class Foo{\n"+
   "   private static var c =[\n"+
   "      new String(`check`),\n"+
   "      new Integer(1)\n"+
   "   ];\n"+
   "   public static var turn =\n"+
   "      c[0].toString();\n"+
   "\n"+
   "   public static getTurn(){\n"+
   "      return turn;\n"+
   "   }\n"+
   "}\n"+
   "println(Foo.turn);\n";
         
         
   public void testStatic() throws Exception {
      assertCompileAndExecuteSuccess(SOURCE_1);
      assertCompileAndExecuteSuccess(SOURCE_2);
   }
}
