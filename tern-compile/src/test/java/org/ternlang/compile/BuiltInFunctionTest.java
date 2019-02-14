package org.ternlang.compile;

public class BuiltInFunctionTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "println(\"x\");\n"+
   "print(\"xx\");\n"+
   "println(time());\n"+
   "eval(\"blah('foo')\");\n"+
   "\n"+
   "function blah(x){\n"+
   "   println(\"blah(\"+x+\")\");\n"+
   "}\n";
   
   private static final String SOURCE_2 =
   "exec('java -version', System.getProperty('java.home') + '/bin');\n";
         
   private static final String SOURCE_3 =
   "const vars = [];\n"+
   "exec('env', {'SOME_ENV': 'blah'})\n"+
   "   .forEachRemaining(line -> vars.add(line));\n"+
   "\n"+
   "assert 'blah' == vars.stream()\n"+
   "   .filter(line -> line.startsWith('SOME_ENV'))\n"+
   "   .map(line -> line.split('=')[1])\n"+
   "   .findFirst()\n"+
   "   .get();\n";     
   
   private static final String SOURCE_4 =
   "const vars = [];\n"+
   "exec('env', System.getProperty('java.home') + '/bin', {'SOME_ENV': 'blah'})\n"+
   "   .forEachRemaining(line -> vars.add(line));\n"+
   "\n"+
   "assert 'blah' == vars.stream()\n"+
   "   .filter(line -> line.startsWith('SOME_ENV'))\n"+
   "   .map(line -> line.split('=')[1])\n"+
   "   .findFirst()\n"+
   "   .get();\n";
   
   public void testBuiltInFunctions() throws Exception{
      assertScriptExecutes(SOURCE_1);
   }
   
   public void testExecFunction() throws Exception{
      assertScriptExecutes(SOURCE_2);
   }
   
   public void testExecFunctionWithEnvVars() throws Exception{
      assertScriptExecutes(SOURCE_3);
   }
   
   public void testExecFunctionWithEnvVarsAndWorkingDir() throws Exception{
      assertScriptExecutes(SOURCE_4);
   }
}
