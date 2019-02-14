package org.ternlang.compile;

public class FunctionInPackageTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "import blah.Bar;\n"+
   "const bb: Bar = new Bar(1,2);\n"+
   "\n"+
   "function blah(a, b){\n"+
   "  return \"wrong!\";\n"+   
   "}"+   
   "assert bb.get() == 'blah(1,2)';\n";
         
   private static final String SOURCE_2 =
   "class Bar{\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   get(){\n"+
   "      return blah(x,y);\n"+
   "   }\n"+   
   "}"+
   "function blah(a, b){\n"+
   "  return \"blah(${a},${b})\";\n"+   
   "}";
         
   public void testPackageWithFunction() throws Exception {
      addScript("/test.tern", SOURCE_1);
      addScript("/blah/Bar.tern", SOURCE_2);
      assertScriptExecutes("/test.tern");
      assertScriptExecutes("/blah/Bar.tern");
   }
}
