package tern.compile;

public class InnerClassTest extends ScriptTestCase {
   
   private static final String SOURCE_1=
   "class Outer {\n"+
   "\n"+
   "   class Inner {\n"+
   "      var x;\n"+
   "      var y;\n"+
   "      new(x,y){\n"+
   "         this.x=x;\n"+
   "         this.y=y;\n"+
   "      }\n"+
   "      dump(){\n"+
   "         println(\"x=${x} y=${y}\");\n"+
   "      }\n"+
   "   }\n"+
   "   create(x,y){\n"+
   "      return new Inner(x,y);\n"+
   "   }\n"+
   "}\n"+
   "var inner = new Outer.Inner(1,2);\n"+
   "inner.dump();\n"+
   "var outer = new Outer();\n"+
   "var result = outer.create(33,44);\n"+
   "result.dump();\n";
   
   private static final String SOURCE_2=
   "class Parent {\n"+
   "\n"+
   "   enum Color {\n"+
   "      RED,\n"+
   "      GREEN,\n"+
   "      BLUE\n"+
   "   }\n"+
   "   dump() {\n"+
   "      println(Color.RED);\n"+
   "   }\n"+
   "}\n"+
   "var parent = new Parent();\n"+
   "parent.dump();\n";
   
   private static final String SOURCE_3 =
   "class Outer {\n"+
   "   abstract class AbstractInner {\n"+
   "      var x;\n"+
   "      var y;\n"+
   "      new(x,y){\n"+
   "         this.x=x;\n"+
   "         this.y=y;\n"+
   "      }\n"+
   "      foo() {\n"+
   "         return `AbstractInner.foo(): x=${x} y=${y}`;\n"+
   "      }\n"+
   "   }\n"+
   "\n"+
   "   class Inner extends AbstractInner{\n"+
   "      new(x,y): super(x,y){}\n"+
   "      dump(){\n"+
   "         println(`x=${x} y=${y}`);\n"+
   "      }\n"+
   "   }\n"+
   "   static create(x,y): AbstractInner {\n"+
   "      return new Inner(x,y);\n"+
   "   }\n"+
   "}\n"+
   "assert Outer.create(1,2).foo() == 'AbstractInner.foo(): x=1 y=2';\n"+
   "println(Outer.create(1,2).foo());\n";           
   
   private static final String SOURCE_4 =
   "trait Outer {\n"+
   "\n"+
   "   blah(x: AbstractInner){\n"+
   "      return x.foo();\n"+
   "   }\n"+
   "\n"+
   "   blah(x: Inner){\n"+
   "      return x.foo();\n"+
   "   }\n"+   
   "\n"+
   "   abstract class AbstractInner {\n"+
   "      var x;\n"+
   "      var y;\n"+
   "      new(x,y){\n"+
   "         this.x=x;\n"+
   "         this.y=y;\n"+
   "      }\n"+
   "      foo() {\n"+
   "         return `AbstractInner.foo(): x=${x} y=${y}`;\n"+
   "      }\n"+
   "   }\n"+
   "\n"+
   "   class Inner extends AbstractInner{\n"+
   "      new(x,y): super(x,y){}\n"+
   "      dump(){\n"+
   "         println(`x=${x} y=${y}`);\n"+
   "      }\n"+
   "   }\n"+
   "   static create(x,y): AbstractInner {\n"+
   "      return new Inner(x,y);\n"+
   "   }\n"+
   "}\n"+
   "assert Outer.create(1,2).foo() == 'AbstractInner.foo(): x=1 y=2';\n"+
   "println(Outer.create(1,2).foo());\n";     
   
   public void testInnerClass() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
      assertScriptExecutes(SOURCE_4);        
   }
}
