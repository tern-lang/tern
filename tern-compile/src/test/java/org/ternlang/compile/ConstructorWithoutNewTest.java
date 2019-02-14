package org.ternlang.compile;

public class ConstructorWithoutNewTest extends ScriptTestCase {

   private static final String SOURCE =
   "let p = Point(1, 2);\n"+
   "println(p);\n"+
   "\n"+
   "class Point{\n"+
   "   let a, b;\n"+
   "   new(a, b){\n"+
   "      this.a = a;\n"+
   "      this.b = a;\n"+
   "   }\n"+
   "}\n";
         
   public void testConstructor() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
