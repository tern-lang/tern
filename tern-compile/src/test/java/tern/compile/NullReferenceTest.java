package tern.compile;

import tern.compile.staticanalysis.CompileTestCase;
import tern.core.Context;

public class NullReferenceTest extends ScriptTestCase {
   
   private static final String SOURCE =
   "class Foo{\n"+
   "   static call<T: Number>(a: T): List<T> {\n"+
   "      return [a];\n"+
   "   }\n"+
   "}\n"+
   "let f = Foo.call<Double>(null).get(0).floatValue();\n"+
   "println(f);\n";
         
   
   public void testNullReference() throws Exception {
      assertScriptExecutes(SOURCE, new AssertionCallback() {
         public void onSuccess(Context context, Object result) throws Exception{
            throw new IllegalStateException("Should be a null pointer");
         }
         public void onException(Context context, Exception cause) throws Exception{
            cause.printStackTrace();
         }
      });
   }

}
