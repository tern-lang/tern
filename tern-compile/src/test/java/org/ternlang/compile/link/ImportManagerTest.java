package org.ternlang.compile.link;

import org.ternlang.compile.ScriptTestCase;
import org.ternlang.compile.Compiler;
import org.ternlang.core.Context;
import org.ternlang.core.link.ImportManager;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.ModuleRegistry;

public class ImportManagerTest extends ScriptTestCase {
   
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
   "import com.test.foo.Foo;\n"+
   "import com.test.foo.Bar;\n"+
   "\n"+
   "class Demo{\n"+
   "   var foo;\n"+
   "   new(x,y){\n"+
   "      this.foo = new Foo(x,y);\n"+
   "   }\n"+
   "   get(): Foo{\n"+
   "      return foo;\n"+
   "   }\n"+
   "}";

   private static final String SOURCE_4 =
   "import com.test.demo.Demo;\n"+
   "\n"+
   "class Builder{\n"+
   "   var demo;\n"+
   "   new(text){\n"+
   "      this.demo = new Demo(text,1);\n"+
   "   }\n"+
   "   get(): Demo{\n"+
   "      return demo;\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_5 =
   "import com.test.foo.Bar;\n"+
   "import com.test.demo.Demo;\n"+   
   "\n"+
   "var bar = new Bar(\"test\");\n"+
   "var demo = new Demo(\"x\",33);\n"+
   "var foo = demo.get();\n"+
   "\n"+
   "println(bar);\n"+
   "println(foo);\n";     
   
   private static final String SOURCE_6 =
   "import com.test.builder.Builder;\n"+
   "\n"+
   "var example = new Builder(\"test\");\n"+
   "var demo = example.get();\n"+
   "\n"+
   "println(demo);\n";   
   
   private static final String SOURCE_7 =
   "module Shape {\n"+
   "\n"+
   "   class Point{\n"+
   "      const x,y;\n"+
   "      new(x,y){\n"+
   "         this.x =x;\n"+
   "         this.y =y;\n"+
   "      }\n"+
   "      show(){\n"+
   "         return `${x},${y}`;\n"+
   "      }\n"+
   "      toString(){\n"+
   "         return `Point(${x},${y})`;\n"+
   "      }\n"+
   "   }\n"+
   "\n"+
   "   class Line {\n"+
   "      const a,b;\n"+
   "      new(a,b){\n"+
   "         this.a=a;\n"+
   "         this.b=b;\n"+
   "      }\n"+
   "      show(){\n"+
   "         return `${a}->${b}`;\n"+
   "      }\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_8 =
   "import com.test.graphics.*;\n"+
   "\n"+
   "var a = new Shape.Point(1,2);\n"+
   "var b = new Shape.Point(2,1);\n"+
   "var l = new Shape.Line(a,b);\n"+
   "\n"+
   "println(l);\n"+
   "\n"+
   "assert a.x==1;\n"+
   "assert a.y==2;\n"+
   "assert b.x==2;\n"+
   "assert b.y==1;\n"+
   "assert l.a==a;\n"+
   "assert l.b==b;\n";
   
   public void testImports() throws Exception {
      addScript("/com/test/foo/Foo.tern", SOURCE_1);
      addScript("/com/test/foo/Bar.tern", SOURCE_2);
      addScript("/com/test/demo/Demo.tern", SOURCE_3);
      addScript("/com/test/builder/Builder.tern", SOURCE_4);
      addScript("/com/test/main.tern", SOURCE_5);
      addScript("/com/test/launch.tern", SOURCE_6);
      addScript("/com/test/graphics/Shape.tern", SOURCE_7);
      addScript("/com/test/draw.tern", SOURCE_8);
      assertScriptExecutes("/com/test/main.tern");
      assertScriptExecutes("/com/test/launch.tern");
      assertScriptExecutes("/com/test/draw.tern");
      
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
      
      Module graphicsModule = registry.addModule("com.test.graphics");
      ImportManager graphicsManager = graphicsModule.getManager();
      
      assertEquals(graphicsManager.getImport("Shape").get().getName(), "com.test.graphics.Shape");
      assertNull(graphicsModule.getType("Shape"));
      assertEquals(graphicsModule.getModule("Shape").getName(), "com.test.graphics.Shape");
      assertEquals(graphicsModule.getModule("Shape").getType("Point").getName(), "Point");
   }
}
