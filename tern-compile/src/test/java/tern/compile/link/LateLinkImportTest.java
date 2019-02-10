package tern.compile.link;

import tern.compile.ScriptTestCase;
import tern.compile.Compiler;
import tern.core.Context;
import tern.core.link.ImportManager;
import tern.core.module.Module;
import tern.core.module.ModuleRegistry;

public class LateLinkImportTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "import util.regex.Pattern;\n"+
   "class Foo {\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Foo(${x},${y})\";\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_2 =
   "class Bar{\n"+
   "   var text;\n"+
   "   new(text){\n"+
   "      this.text=text;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Bar(${text})\";\n"+
   "   }\n"+
   "}\n";         
   
   private static final String SOURCE_3 =
   "func main(){\n" +      
   "  var bar = new Bar(\"test\");\n"+
   "  var foo = new Foo(\"x\",33);\n"+
   "\n"+
   "  println(bar);\n"+
   "  println(foo);\n"+
   "}\n";
   
   private static final String SOURCE_4 =
   "func main(){\n" +      
   "  var bar = Bar(\"test\");\n"+
   "  var foo = Foo(\"x\",33);\n"+
   "\n"+
   "  println(bar);\n"+
   "  println(foo);\n"+
   "}\n";
   
   private static final String SOURCE_5 =
   "class Main {\n"+      
   "\n"+
   "  start(){\n" +      
   "     var bar = Bar(\"test\");\n"+
   "     var foo = Foo(\"x\",33);\n"+
   "     \n"+
   "     println(bar);\n"+
   "     println(foo);\n"+
   "  }\n"+
   "}";
   
   
   public void testLateLinkImports() throws Exception {
      addScript("/com/test/foo/Foo.tern", SOURCE_1);
      addScript("/com/test/foo/Bar.tern", SOURCE_2);
      addScript("/com/test/foo.tern", SOURCE_3);
      assertExpressionEvaluates("/com/test/foo.tern", "main()", "com.test.foo");
      
      AssertionContext assertion = getContext();
      Context context = assertion.getContext();
      Compiler compiler = assertion.getCompiler();
      ModuleRegistry registry = context.getRegistry();
      Module fooModule = registry.addModule("com.test.foo");
      ImportManager fooManager = fooModule.getManager();
      
      assertEquals(fooManager.getImport("Bar").get().getName(), "Bar");
      assertEquals(fooManager.getImport("Foo").get().getName(), "Foo");
      assertEquals(fooModule.getType("Bar").getName(), "Bar");
      assertEquals(fooModule.getType("Foo").getName(), "Foo");
      
      assertNull(fooManager.getImport("Missing"));
      assertNull(fooModule.getType("Missing"));
      assertNull(fooModule.getModule("Missing"));
   }
   
   public void testLateLinkImportsAsFunctions() throws Exception {
      addScript("/com/test/foo/Foo.tern", SOURCE_1);
      addScript("/com/test/foo/Bar.tern", SOURCE_2);
      addScript("/com/test/foo.tern", SOURCE_4); // here we do no use the 'new; keyword
      assertExpressionEvaluates("/com/test/foo.tern", "main()", "com.test.foo");
   }
   
   public void testLateLinkImportsAsFunctionsInType() throws Exception {
      addScript("/com/test/foo/Foo.tern", SOURCE_1);
      addScript("/com/test/foo/Bar.tern", SOURCE_2);
      addScript("/com/test/foo.tern", SOURCE_5); // here we do no use the 'new; keyword from a type
      assertExpressionEvaluates("/com/test/foo.tern", "new Main().start()", "com.test.foo");
   }
}
