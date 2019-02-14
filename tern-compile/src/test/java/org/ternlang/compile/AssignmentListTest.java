package org.ternlang.compile;

public class AssignmentListTest extends ScriptTestCase {
   
   private static final String SOURCE_1=
   "let a = 1;\n"+
   "let b = 2;\n"+
   "\n"+
   "(a, b) = (10, 11);\n"+
   "\n"+
   "assert a == 10;\n"+
   "assert b == 11;\n";
   
   private static final String SOURCE_2=
   "func fib() {\n"+
   "    let a = 0, b = 1;\n"+
   "    loop {\n"+
   "        yield a;\n"+
   "        (a, b) = (b, a + b);\n"+
   "   }\n"+
   "}\n"+
   "let it = fib().iterator();\n"+
   "\n"+
   "for(i in 0..40){\n"+
   "   let index = i - 1;\n"+
   "   let value = it.next;\n"+
   "\n"+
   "   if(index == 30) {\n"+
   "      assert value == 1346269;\n"+
   "   }\n"+
   "   println(`${index}=${value}`);\n"+
   "}\n";
   
   private static final String SOURCE_3=
   "let a = 1;\n"+
   "let b = 2;\n"+
   "\n"+
   "(a, b) = (b, a);\n"+
   "\n"+
   "assert a == 2;\n"+
   "assert b == 1;\n";
   
   public void testAssignmentListStatement() throws Exception{
      assertScriptExecutes(SOURCE_1);
   }
   
   public void testAssignmentListInFunction() throws Exception{
      assertScriptExecutes(SOURCE_2);
   }
   
   public void testAssignmentListSwap() throws Exception{
      assertScriptExecutes(SOURCE_3);
   }
}
