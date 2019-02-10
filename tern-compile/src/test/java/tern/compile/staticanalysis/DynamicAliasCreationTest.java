package tern.compile.staticanalysis;

import tern.compile.ClassPathCompilerBuilder;
import tern.compile.Compiler;

import junit.framework.TestCase;

public class DynamicAliasCreationTest extends TestCase {

   private static final String SOURCE_1 =
   "class Foo{\n"+
   "   const x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   override toString(){\n"+
   "      `Foo(${x},${y})`;\n"+
   "   }\n"+
   "}\n"+
   "var t = Foo.class;\n"+
   "var package = Foo.class.getModule();\n"+
   "var context = package.getContext();\n"+
   "var loader = context.getLoader();\n"+
   "var name = package.getName() +'.' + t.getName();\n"+
   "var data = package.getManager().addImport(name, 'X');\n"+
   "\n"+
   "println(name);\n"+
   "\n"+
   "assert eval('new X(13,32)').toString() == 'Foo(13,32)';\n"+
   "assert eval('new X(13,32)').x == 13;\n"+
   "assert eval('new X(13,32)').y == 32;\n";

   private static final String SOURCE_2 =
   "class Foo{\n"+
   "   const x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   override toString(){\n"+
   "      `Foo(${x},${y})`;\n"+
   "   }\n"+
   "}\n"+
   "class Test {\n"+
   "   test() {\n"+
   "     var t = Foo.class;\n"+
   "     var package = Foo.class.getModule();\n"+
   "     var context = package.getContext();\n"+
   "     var loader = context.getLoader();\n"+
   "     var name = package.getName() +'.' + t.getName();\n"+
   "     var data = package.getManager().addImport(name, 'X');\n"+
   "     \n"+
   "     println(name);\n"+
   "     \n"+
   "     assert eval('new X(13,32)').toString() == 'Foo(13,32)';\n"+
   "     assert eval('new X(13,32)').x == 13;\n"+
   "     assert eval('new X(13,32)').y == 32;\n"+
   "  }"+
   "}\n"+
   "new Test().test();";
   
   public void testEnsureWeCanCreateADynamicAlias() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
   }
   
   public void testEnsureWeCanCreateADynamicAliasFromClass() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute();
   }
}
