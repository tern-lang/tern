package org.ternlang.compile;

import junit.framework.TestCase;

public class ExtensionTest extends ScriptTestCase {

   private static final String SOURCE_1 = 
   "var list = new File(\".\").findFiles(\".*\");\n"+
   "println(list);\n";
   
   private static final String SOURCE_2 = 
   "println(new Date().getYear());\n";
   
   private static final String SOURCE_3 = 
   "let x = URL('http://www.google.com').get().response();\n"+
   "println(x);\n"+
   "println(x);";

   private static final String SOURCE_4 =
   "let f = File.createTempFile('FileExtensionTest', 'tmp');\n"+
   "let a = ['line one', 'line two', 'last line'];\n"+
   "f.writeText('line one\nline two\nlast line\n');\n"+
   "f.forEachLine(l -> {\n"+
   "   let next = a.remove(0);\n"+
   "   println(l);\n" +
   "   assert next == l;\n"+
   "});";

   private static final String SOURCE_5 =
   "let x = 1.230000001d;\n"+
   "assert x.round() == 1;\n"+
   "assert x.round(1) == 1.2;\n"+
   "assert x.round(2) == 1.23;\n"+
   "assert x.round(3) == 1.23;\n"+
   "assert x.round(4) == 1.23;\n"+
   "assert x.round(9) == 1.230000001;\n";

   private static final String SOURCE_6 =
   "let x = 1.2300001f;\n"+
   "assert x.round() == 1;\n"+
   "assert x.round(1) == 1.2;\n"+
   "assert x.round(2) == 1.23;\n"+
   "assert x.round(3) == 1.23;\n"+
   "assert x.round(4) == 1.23;\n"+
   "assert x.round(7) == 1.2300001;\n";

   private static final String SOURCE_7 =
   "let x = 1;\n"+
   "assert x.round() == 1;\n"+
   "assert x.round(1) == 1;\n"+
   "assert x.round(2) == 1;\n"+
   "assert x.round(3) == 1;\n"+
   "assert x.round(4) == 1;\n"+
   "assert x.round(9) == 1;\n";

   private static final String SOURCE_8 =
   "import util.concurrent.atomic.AtomicInteger;\n"+
   "let x = new AtomicInteger(12);\n"+
   "assert x.round().get() == 12;\n"+
   "assert x.round(1).get() == 12;\n"+
   "assert x.round(2).get() == 12;\n"+
   "assert x.round(3).get() == 12;\n"+
   "assert x.round(4).get() == 12;\n"+
   "assert x.round(9).get() == 12;\n";

   private static final String SOURCE_9 =
   "let x: Number = 12;\n"+
   "assert x.round() == 12;\n"+
   "assert x.round(1) == 12;\n"+
   "assert x.round(2) == 12;\n"+
   "assert x.round(3) == 12;\n"+
   "assert x.round(4) == 12;\n"+
   "assert x.round(9) == 12;\n";

   public void testFileExtension() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
      assertScriptExecutes(SOURCE_4);
      assertScriptExecutes(SOURCE_5);
      assertScriptExecutes(SOURCE_6);
      assertScriptExecutes(SOURCE_7);
      assertScriptExecutes(SOURCE_8);
      assertScriptExecutes(SOURCE_9);
   }  
}
