package org.ternlang.compile;

public class ArrayElementCoercionTest extends ScriptTestCase {
   
   private static final String SOURCE =
   "var s : String[][] = [['a','b'],[]];\n"+
   "var i : Integer[][] = [['1','2', 3.0d, 11L],[2]];\n"+
   "var b : Byte[] = ['99','100', 111.0d, 120L];\n"+
   "println(s);\n"+
   "println(i);\n"+
   "println(b);\n"+
   "var buffer = new ByteArrayOutputStream();\n"+
   "buffer.write(b);\n"+
   "println(buffer);\n";
   
   public void testElementConversion() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
