package tern.compile.staticanalysis;

public class FieldDeclarationOrderTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "class Foo{\n"+
   "   func(){\n"+
   "      x=10;\n"+
   "   }\n"+
   "   var x=11;\n"+
   "}\n";  

   private static final String SUCCESS_2 =
   "module Foo{\n"+
   "   func(){\n"+
   "      x=10;\n"+
   "   }\n"+
   "   var x:String='x';\n"+
   "}\n";
   
   private static final String FAILURE_1 =
   "module Foo{\n"+
   "   func(){\n"+
   "      x.intValue();\n"+
   "   }\n"+
   "   var x:String='x';\n"+
   "}\n";   
   
   public void testThatFieldOrderDoesNotMatter() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileError(FAILURE_1, "Function 'intValue()' not found for 'lang.String' in /default.tern at line 3");
   }

}
