package org.ternlang.compile;

import org.ternlang.compile.staticanalysis.CompileTestCase;

public class FunctionCallWithArgumentNamesTest extends CompileTestCase {

   private static final String SUCCESS_1 = 
   "foo(a: 1, b: 2, c: 3);\n"+
   "\n"+
   "func foo(a, b, c) {\n"+
   "   println(`${a},${b},${c}`);\n"+
   "}\n";
   
   private static final String SUCCESS_2 = 
   "foo(a: 1, 2, 3);\n"+
   "\n"+
   "func foo(a, b, c) {\n"+
   "   println(`${a},${b},${c}`);\n"+
   "}\n";
   
   private static final String SUCCESS_3 =
   "class Point {\n"+
   "   const x;\n"+
   "   const y;\n"+
   "\n"+
   "   new(x, y) {\n"+
   "      this.x = x;\n"+
   "      this.y = y;\n"+
   "   }\n"+
   "}\n"+
   "let p = Point(x: 1, y: 33);\n"+
   "\n"+
   "assert p.x == 1;\n"+
   "assert p.y == 33;\n";
   
   private static final String SUCCESS_4 =
   "class Shape {\n"+
   "   const p: Point;\n"+
   "   new(a, b) {\n"+
   "      this.p = new Point(x: a, y: b);\n"+
   "   }\n"+
   "   class Point {\n"+
   "      const x: Number;\n"+
   "      const y: Number;\n"+
   "      new(x: Number, y: Number) {\n"+
   "         this.x = x;\n"+
   "         this.y = y;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "let s = Shape(31,23);\n"+
   "assert s.p.x == 31;\n"+
   "assert s.p.y == 23;\n"; 
   
   private static final String SUCCESS_5 =
   "class Shape {\n"+
   "   const name;\n"+
   "   new(name){\n"+
   "      this.name = name;\n"+
   "   }\n"+
   "}\n"+
   "class Circle extends Shape {\n"+
   "   const radius;\n"+
   "   const color;\n"+
   "   new(radius): this(radius: radius, color: '#ffffff'){}\n"+
   "   new(radius, color): super(name: 'circle') {\n"+
   "      this.radius = radius;\n"+
   "      this.color = color;\n"+
   "   }\n"+
   "}\n";
   
   private static final String SUCCESS_6 =
   "class Shape {\n"+
   "   const p: Point;\n"+
   "   new(p: Point) {\n"+
   "      this.p = new Point(x: 1, y: 2);\n"+
   "   }\n"+
   "   class Point {\n"+
   "      const x: Number;\n"+
   "      const y: Number;\n"+
   "      new(x: Number, y: Number) {\n"+
   "         this.x = x;\n"+
   "         this.y = y;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "const p: Shape.Point = null;\n"+
   "const s: Shape = Shape(p);\n"+
   "assert s.p.x == 1;\n"+
   "assert s.p.y == 2;\n";

   private static final String SUCCESS_7 =
   "class Shape {\n"+
   "   const p: Point;\n"+
   "   new(p: Point) {\n"+
   "      this.p = new Point(x: p.y, y: p.x);\n"+
   "   }\n"+
   "   class Point {\n"+
   "      const x: Number;\n"+
   "      const y: Number;\n"+
   "      new(x: Number, y: Number) {\n"+
   "         this.x = x;\n"+
   "         this.y = y;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "const p: Shape.Point = Shape.Point(x: 1, y: 2);\n"+
   "const s: Shape = Shape(p);\n"+
   "assert s.p.x == 2;\n"+
   "assert s.p.y == 1;\n";

   private static final String FAILURE_1 = 
   "foo(b: 1, 2, 3);\n"+
   "\n"+
   "func foo(a, b, c) {\n"+
   "   println(`${a},${b},${c}`);\n"+
   "}\n";
   
   private static final String FAILURE_2 = 
   "class Foo {\n"+
   "   blah(x, y) {}\n"+
   "}\n"+
   "new Foo().blah(y: 1, x: 2);\n";
   
   private static final String FAILURE_3 =
   "class Point {\n"+
   "   const x;\n"+
   "   const y;\n"+
   "\n"+
   "   new(x, y) {\n"+
   "      this.x = x;\n"+
   "      this.y = y;\n"+
   "   }\n"+
   "}\n"+
   "let p = Point(y: 1, x: 33);\n"+
   "println(p);\n";
   
   private static final String FAILURE_4 =
   "class Point {\n"+
   "   const x;\n"+
   "   const y;\n"+
   "\n"+
   "   new(x, y) {\n"+
   "      this.x = x;\n"+
   "      this.y = y;\n"+
   "   }\n"+
   "}\n"+
   "let p = new Point(y: 1, x: 33);\n"+
   "println(p);\n";  
   
   private static final String FAILURE_5 =
   "class Shape {\n"+
   "   const p: Point;\n"+
   "   new(a, b) {\n"+
   "      this.p = new Point(y: a, y: b);\n"+
   "   }\n"+
   "   class Point {\n"+
   "      const x: Number;\n"+
   "      const y: Number;\n"+
   "      new(x: Number, y: Number) {\n"+
   "         this.x = x;\n"+
   "         this.y = y;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "let s = Shape(31,23);\n"+
   "assert s.p.x == 31;\n"+
   "assert s.p.y == 23;\n";
   
   private static final String FAILURE_6 =
   "class Shape {\n"+
   "   const name;\n"+
   "   new(name){\n"+
   "      this.name = name;\n"+
   "   }\n"+
   "}\n"+
   "class Circle extends Shape {\n"+
   "   const radius;\n"+
   "   const color;\n"+
   "   new(radius): this(radius: radius, color: '#ffffff'){}\n"+
   "   new(radius, color): super(color: 'circle') {\n"+
   "      this.radius = radius;\n"+
   "      this.color = color;\n"+
   "   }\n"+
   "}\n";
   
   private static final String FAILURE_7 =
   "class Shape {\n"+
   "   const p: Point;\n"+
   "   new(a, b) {\n"+
   "      this.p = Point(y: a, y: b);\n"+
   "   }\n"+
   "   class Point {\n"+
   "      const x: Number;\n"+
   "      const y: Number;\n"+
   "      new(x: Number, y: Number) {\n"+
   "         this.x = x;\n"+
   "         this.y = y;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "let s = Shape(a: 31, b: 23);\n"+
   "assert s.p.x == 31;\n"+
   "assert s.p.y == 23;\n";    
   
   public void testArgumentNames() throws Exception {
      assertCompileAndExecuteSuccess(SUCCESS_1);
      assertCompileAndExecuteSuccess(SUCCESS_2);
      assertCompileAndExecuteSuccess(SUCCESS_3);
      assertCompileAndExecuteSuccess(SUCCESS_4);
      assertCompileAndExecuteSuccess(SUCCESS_5);
      assertCompileAndExecuteSuccess(SUCCESS_6);
      assertCompileAndExecuteSuccess(SUCCESS_7);
      assertCompileError(FAILURE_1, "Function 'foo(lang.Integer, lang.Integer, lang.Integer)' has an invalid argument name in /default.tern at line 1");
      assertCompileError(FAILURE_2, "Function 'blah(lang.Integer, lang.Integer)' has an invalid argument name in /default.tern at line 4");
      assertCompileError(FAILURE_3, "Constructor 'new(lang.Integer, lang.Integer)' has an invalid argument name in /default.tern at line 10");
      assertCompileError(FAILURE_4, "Constructor 'new(lang.Integer, lang.Integer)' has an invalid argument name in /default.tern at line 10");
      assertCompileError(FAILURE_5, "Constructor 'new(default.Any, default.Any)' has an invalid argument name in /default.tern at line 4");
      assertCompileError(FAILURE_6, "Constructor 'new(lang.String)' has an invalid argument name in /default.tern at line 7");
      assertCompileError(FAILURE_7, "Constructor 'new(default.Any, default.Any)' has an invalid argument name in /default.tern at line 4");
   }
  
}
