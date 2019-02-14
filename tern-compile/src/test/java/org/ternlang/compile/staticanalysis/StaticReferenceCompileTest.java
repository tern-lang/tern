package org.ternlang.compile.staticanalysis;

public class StaticReferenceCompileTest extends CompileTestCase {

   public static final String SUCCESS_1 = 
   "class Z {\n"+
   "   var v = AN_R+2; // non-static reference to static\n"+
   "   static const AN_R=Short.MAX_VALUE-2;\n"+
   "   getR(){\n"+
   "      return AN_R;\n"+
   "   }\n"+
   "   getV(){\n"+
   "      return v;\n"+
   "   }\n"+
   "}\n";
 
   public void testReferenceStaticVariable() throws Exception {
      assertCompileSuccess(SUCCESS_1);
   }
}
