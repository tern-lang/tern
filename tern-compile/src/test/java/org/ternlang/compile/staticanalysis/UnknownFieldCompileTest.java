package org.ternlang.compile.staticanalysis;

public class UnknownFieldCompileTest extends CompileTestCase {

   public static final String FAILURE_1 = 
   "class Color {\n"+
   "   var a;\n"+
   "   var b;\n"+
   "   var c;\n"+
   "   func(){\n"+
   "      println(this.unknown);\n"+
   "   }\n"+
   "}\n"+
   "new Color().func();\n";

   public void testInvalidVariable() throws Exception {
      assertCompileError(FAILURE_1, "Property 'unknown' not found for 'default.Color' in /default.tern at line 6");
   }
}
