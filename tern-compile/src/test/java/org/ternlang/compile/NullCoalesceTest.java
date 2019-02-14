package org.ternlang.compile;

public class NullCoalesceTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "var x=null;\n"+
   "var y=11;\n"+
   "assert (null===x??y)==false;\n";
   
   public static final String SOURCE_2 =
   "let v = 1;\n"+
   "assert v == 1;\n"+
   "let y = v ?? v++;\n"+
   "assert v == 1;\n"+
   "assert y == 1;\n"+
   "let z = v ?? v++;\n"+
   "assert v == 1;\n"+
   "assert y == 1;\n"+
   "assert z == 1;\n";        

   public void testNullCoalesce() throws Exception{
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
   }
}
