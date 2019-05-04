package org.ternlang.compile.staticanalysis;

public class SuperTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "class Base {\n"+
   "   var a;\n"+
   "   var b;\n"+
   "   new(a,b){\n"+
   "      this.a = a;\n"+
   "      this.b = b;\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class Concrete extends Base {\n"+
   "   new(a, b) : super(a, b){\n"+
   "      System.err.println(\"a=\"+a+\" b=\"+b);\n"+
   "   }\n"+
   "   dump(){\n"+
   "      System.err.println(\"dump(a=\"+a+\" b=\"+b+\")\");\n"+
   "   }\n"+
   "}\n"+
   "var c = new Concrete(1,2);\n"+
   "c.dump();\n";
   
   private static final String SUCCESS_2 =
   "class Foo {\n"+
   "   new(a): super(){}\n"+
   "}\n";
   
   private static final String SUCCESS_3 =
   "class Foo {\n"+
   "   new(a, b): this(a){}\n"+
   "   new(a): super(){}\n"+
   "}\n"+
   "new Foo(1,2);\n";

   private static final String FAILURE_1 =
   "class Foo {\n"+
   "   new(a): super(a){}\n"+
   "}\n";

   private static final String FAILURE_2 =
   "class Foo {\n"+
   "   new(a): super(a, 1){}\n"+
   "}\n";
   
   private static final String FAILURE_3 =
   "class Foo {\n"+
   "   new(a, b): this(){}\n"+
   "   new(a): super(){}\n"+
   "}\n"+
   "new Foo(1,2);\n";
   
   private static final String FAILURE_4 =
   "class Bar {\n"+
   "   new(a){}\n"+
   "}\n"+
   "class Foo extends Bar{\n"+
   "   new(a,b): super(){}\n"+
   "}\n";
   
   private static final String FAILURE_5 =
   "class Foo{\n"+
   "   new(a): super(a, v, c){}\n"+
   "}\n"+
   "new Foo(1);\n";
   
   public void testSuper() throws Exception{
      assertCompileAndExecuteSuccess(SUCCESS_1);
      assertCompileAndExecuteSuccess(SUCCESS_2);
      assertCompileAndExecuteSuccess(SUCCESS_3);
      assertCompileError(FAILURE_1, "Constructor 'new(default.Any)' not found for 'default.Any' in /default.tern at line 1");
      assertCompileError(FAILURE_2, "Constructor 'new(default.Any, lang.Integer)' not found for 'default.Any' in /default.tern at line 1");
      assertCompileError(FAILURE_3, "Constructor 'new()' not found for 'default.Foo' in /default.tern at line 1");
      assertCompileError(FAILURE_4, "Constructor 'new()' not found for 'default.Bar' in /default.tern at line 4");
      assertCompileError(FAILURE_5, "Property 'v' not found in scope in /default.tern at line 1");
   }

}
