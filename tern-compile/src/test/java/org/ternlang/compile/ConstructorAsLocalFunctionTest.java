package org.ternlang.compile;

public class ConstructorAsLocalFunctionTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "var x = StringBuilder();\n"+
   "x.append('x');\n"+
   "assert x.toString() == 'x';\n";      
   
   private static final String SOURCE_2 =
   "var x = StringBuilder(`ssss`);\n"+
   "x.append('x');\n"+
   "assert x.toString() == 'ssssx';\n";
   
   private static final String SOURCE_3 =
   "var x = File(System.getProperty('java.io.tmpdir')).listFiles();\n"+
   "x.stream().forEach(e -> println(e));\n"+
   "assert x != null;\n";
   
   public void testConstructorFunctions() throws Exception{
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);  
   }
}
