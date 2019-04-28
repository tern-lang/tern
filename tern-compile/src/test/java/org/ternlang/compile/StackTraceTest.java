package org.ternlang.compile;

import org.ternlang.core.Context;

public class StackTraceTest extends ScriptTestCase {
   
   private static final String SUCCESS_1 =
   "const list: List<String> = ['a', 'b', 'c'];\n"+
   "list.stream()\n"+
   "   .map(x -> x.toUpperCase())\n"+
   "   .filter(Objects::nonNull)\n"+
   "   .map(x -> `${x}${x}`)\n"+
   "   .forEach(x -> {\n"+
   "      throw new Exception(x);\n"+
   "   });\n";
   
   private static final String SUCCESS_2 =
   "class Foo {\n"+
   "   const a, b;\n"+
   "   new(a, b) {\n"+
   "      this.a = a;\n"+
   "      this.b = b.toUpperCase();\n"+
   "   }\n"+
   "}\n"+
   "new Foo(1, null);\n";

   private static final String SUCCESS_3 =
   "class Foo {\n"+
   "   const a, b;\n"+
   "   const c = b.toUpperCase();\n"+         
   "   new(a, b) {\n"+
   "      this.a = a;\n"+
   "      this.b = b.toUpperCase();\n"+
   "   }\n"+
   "}\n"+
   "new Foo(1, 'x');\n";

   public void testStackTraceForStream() throws Exception {
      assertScriptExecutes(SUCCESS_1, new AssertionCallback() {
         @Override
         public void onSuccess(Context context, Object result) throws Exception{
            assertTrue("Should have failed", false);
         }
         @Override
         public void onException(Context context, Exception cause) throws Exception{
            cause.printStackTrace();
            StackTraceElement[] elements = cause.getStackTrace();
            assertEquals(elements[0].toString(), "default.main(/default.tern:2)");
         }
      });
      assertScriptExecutes(SUCCESS_2, new AssertionCallback() {
         @Override
         public void onSuccess(Context context, Object result) throws Exception{
            assertTrue("Should have failed", false);
         }
         @Override
         public void onException(Context context, Exception cause) throws Exception{
            cause.printStackTrace();
            StackTraceElement[] elements = cause.getStackTrace();
            assertEquals(elements[0].toString(), "default.Foo.new(/default.tern:5)");
            assertEquals(elements[1].toString(), "default.main(/default.tern:8)");
         }
      });
      assertScriptExecutes(SUCCESS_3, new AssertionCallback() {
         @Override
         public void onSuccess(Context context, Object result) throws Exception{
            assertTrue("Should have failed", false);
         }
         @Override
         public void onException(Context context, Exception cause) throws Exception{
            cause.printStackTrace();
            StackTraceElement[] elements = cause.getStackTrace();
            assertEquals(elements[0].toString(), "default.main(/default.tern:9)");
         }
      });
   }
}
