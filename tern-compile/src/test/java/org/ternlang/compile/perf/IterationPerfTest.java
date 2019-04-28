package org.ternlang.compile.perf;

import org.ternlang.compile.ScriptTestCase;

public class IterationPerfTest extends ScriptTestCase {

   private static final String SUCCESS_1 =
   "var start = new Date().getTime();\n"+
   "\n"+
   "var pi = 4;\n"+
   "var plus = false;\n"+
   "\n"+
   "for (var i = 3; i < 100000000; i += 2) {\n"+
   "   if (plus) {\n"+
   "      pi += 4.0 / i;\n"+
   "   } else {\n"+
   "      pi -= 4.0 / i;\n"+
   "   }\n"+
   "   plus = !plus;\n"+
   "}\n"+
   "var stop =new Date().getTime();\n"+
   "print(pi+\":\"+(stop -start));\n";
 

   public void testIteration() throws Exception {
      assertScriptExecutes(SUCCESS_1);
   }
}
