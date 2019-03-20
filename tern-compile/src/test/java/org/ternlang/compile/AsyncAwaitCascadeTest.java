package org.ternlang.compile;

public class AsyncAwaitCascadeTest extends ScriptTestCase {

   private static final String SOURCE =
   "let i = 0;\n"+
    "let count = 3500;\n"+
    "\n"+
    "async func one(n){\n"+
    "   if(n <= 0) {\n"+
    "      return 1;\n"+
    "   }\n"+
    "   await two(n-1);\n"+
    "   return i += 2;\n"+
    "}\n"+
    "\n"+
    "async func two(n){\n"+
    "   if(n <= 0) {\n"+
    "      return 1;\n"+
    "   }\n"+
    "   await one(n-1);\n"+
    "   return i += 2;\n"+
    "}\n"+
    "\n"+
    "one(count).join();\n"+
    "println(`i=${i} count*2=${count * 2}`);\n"+
    "assert i == count * 2;\n";

   public void testAsyncAwait() throws Exception {
      assertScriptExecutes(SOURCE);
   }

   protected boolean isThreadPool() {
      return true;
   }
}
