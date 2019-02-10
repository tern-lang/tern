package tern.compile;


public class NestedEvalTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "import util.stream.Collectors;\n"+
   "trait Base{\n"+
    "   execute(name){\n"+
   "       Collectors.toList();\n"+
   "       eval('{:}');\n"+
   "       eval(name);\n"+
   "    }\n"+
   "}\n";
         
   private static final String SOURCE_2 =
   "import base.*;\n"+
   "class Other with Base{\n"+
   "   test(){\n"+
   "      execute('get()');\n"+
   "   }\n"+
   "   get(){\n"+
   "      \"Other.get()\";\n"+
   "   }\n"+
   "}";
   
   private static final String SOURCE_3 =
   "println(eval('new Other().test()'));\n";
         
   public void testImports() throws Exception {
      addScript("/base/Base.snap", SOURCE_1);
      addScript("/test/Other.snap", SOURCE_2);   
      addScript("/test.snap", SOURCE_3);  
      assertScriptExecutes("/test.snap"); 
   }
}
