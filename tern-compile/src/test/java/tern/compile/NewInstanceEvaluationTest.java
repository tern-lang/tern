package tern.compile;

import java.util.Arrays;

import tern.core.Any;
import tern.core.Context;
import tern.core.scope.instance.ObjectInstance;

public class NewInstanceEvaluationTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "import util.stream.Collectors;\n"+
   "trait Base{\n"+
    "   f(name){\n"+
   "       var f = -> Collectors.toList();\n"+
   "       f();\n"+
   "       eval('{:}');\n"+
   "       eval(name);\n"+
   "    }\n"+
   "}\n";
         
   private static final String SOURCE_2 =
   "import base.Base;\n"+
   //"import base.*;\n"+ // this works
   "class Other with Base{\n"+
   "   test(){\n"+
   "      f('get()');\n"+
   "   }\n"+
   "   get(){\n"+
   "      \"Other.get()\";\n"+
   "   }\n"+
   "   override toString(){\n"+
   "     return 'Other.toString()';\n"+
   "   }\n"+
   "}";

   private static final String SOURCE_3 =
   "import test.*;\n"+
   //"import base.*;\n"+ // this works
   "const value = eval('new Other()');\n"+
   "value.test();\n";
   
   public void testEvaluation() throws Exception {
      addScript("/base/Base.tern", SOURCE_1);
      addScript("/test/Other.tern", SOURCE_2);
      assertExpressionEvaluates("/test/Other.tern", "new Other().test()", "test");
      assertExpressionEvaluates("/test/Other.tern", "new Other()", "test", new AssertionCallback() {
         @Override
         public void onSuccess(Context context, Object result) {
            assertNotNull(result);
            assertEquals(result.getClass(), ObjectInstance.class);
            assertEquals(ObjectInstance.class.cast(result).getType().getName(), "Other");
            assertEquals(ObjectInstance.class.cast(result).getType().getModule().getName(), "test");
            assertNull(ObjectInstance.class.cast(result).getType().getType()); // not a platform type i.e a Java type
            assertEquals(result.toString(), "test.Other");   
            
            Object proxy = context.getWrapper().toProxy(result);
            
            assertNotNull(proxy);
            assertTrue(Arrays.asList(proxy.getClass().getInterfaces()).contains(Any.class));
            assertEquals(proxy.toString(), "Other.toString()");
         }
      });
   }
   
   public void testEvaluationReturnValue() throws Exception {
      addScript("/base/Base.tern", SOURCE_1);
      addScript("/test/Other.tern", SOURCE_2);
      addScript("/run.tern", SOURCE_3);
      assertScriptExecutes("/run.tern");
   }
   
}
