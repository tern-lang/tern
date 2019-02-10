package tern.compile;

import tern.core.Bug;
import tern.core.Context;

public class TextTemplateTest extends ScriptTestCase {

   private static final String SOURCE_1=
   "var arr = new Integer[2];\n"+
   "println(\"arr[0]=${arr[0]}\");\n"+
   "println(\"arr[1]=${arr[1]}\");\n"+
   "arr[0]=2133;\n"+
   "assert \"${arr[0]}\" == '2133';\n";
   
   private static final String SOURCE_2 = 
   "function f(){\n"+
   "   var x = 1;\n"+
   "   println(\"x=${x}\");\n"+
   "   assert \"x=${x}\" == 'x=1';\n"+
   "}\n"+
   "f();\n";

   private static final String SOURCE_3 = 
   "var x = 11;\n"+
   "assert `${y} ${x}` == `null 11`;\n";
   
   private static final String SOURCE_4 = 
   "assert `${String.class.type.name}` == 'java.lang.String';\n";
   
   private static final String SOURCE_5 = 
   "function f(){\n"+
   "   return null;\n"+
   "}\n"+
   "println(`val=${f()}`);\n" +
   "assert `val=${f()}` == 'val=null';\n";
   
   private static final String SOURCE_6 = 
   "var x = 11;\n"+
   "var y = null;\n"+
   "assert `${y} ${x}` == `null 11`;\n";
   
   private static final String SOURCE_7 =
   "class Point{\n"+
   "   private let x = 1;\n"+
   "   toString() {\n"+
   "      return `${x}`;\n"+
   "   }\n"+
   "}\n"+
   "println(new Point());\n"+
   "assert new Point().toString() == `1`;\n";
   
   public void testTemplate() throws Exception {
      assertScriptExecutes(SOURCE_1);
   }
   
   public void testTemplateFromFunction() throws Exception {
       assertScriptExecutes(SOURCE_2);
   }
   
   public void testMissingVariable() throws Exception {
      assertScriptExecutes(SOURCE_3, new AssertionCallback() {
         @Override
         public void onSuccess(Context context, Object result) throws Exception{
            throw new IllegalStateException("Should be an error");
         }
         public void onException(Context context, Exception cause) throws Exception{
            cause.printStackTrace();
         }
      });
   }
   
   public void testClassReference() throws Exception {
      assertScriptExecutes(SOURCE_4);
   }
   
   public void testNullReturnedFromFunction() throws Exception {
      assertScriptExecutes(SOURCE_5);
   }
   
   public void testNullVariable() throws Exception {
      assertScriptExecutes(SOURCE_6);
   }
   
   public void testPrivateProperty() throws Exception {
      assertScriptExecutes(SOURCE_7);
   }
}