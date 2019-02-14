package org.ternlang.compile.staticanalysis;

public class PrivateAccessCompileTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "class Typ {\n"+
   "   private var x;\n"+
   "   new(){\n"+
   "      this.x=10;\n"+
   "   }\n"+
   "}";
   
   private static final String SUCCESS_2 =
   "class Typ {\n"+
   "   private const x;\n"+
   "   new(){\n"+
   "      this.x=10;\n"+
   "   }\n"+
   "}";
   
   private static final String SUCCESS_3 =
   "class Supr {\n"+
   "   private var x;\n"+
   "   new(a){\n"+
   "      this.x=a;\n"+
   "   }\n"+
   "}"+
   "class Typ extends Supr{\n"+
   "   new() : super(1){\n"+
   "      assert this.x == 1;\n"+
   "      this.x=10;\n"+
   "   }\n"+
   "}";
   
   private static final String SUCCESS_4 =
   "class Outer{\n"+
   "   private var x;\n"+
   "   class Inner {\n"+
   "      private var o: Outer;\n"+
   "      func(){\n"+
   "         return o.x;\n"+
   "      }\n"+
   "   }\n"+
   "}\n";
   
   private static final String SUCCESS_5 =
   "class Outer{\n"+
   "   private var x: Inner;\n"+
   "   class Inner {\n"+
   "      private var n;\n"+
   "   }\n"+
   "   func(){\n"+
   "      return x.n;\n"+
   "   }\n"+   
   "}\n";
         
   private static final String FAILURE_1 =
   "class Typ {\n"+
   "   private func(){}\n"+
   "}"+
   "new Typ().func();\n";
   
   private static final String FAILURE_2 =
   "class Typ {\n"+
   "   private static func(){}\n"+
   "}"+
   "Typ.func();\n";   
   
   private static final String FAILURE_3 =
   "module Mod {\n"+
   "   private func(){}\n"+
   "}"+
   "Mod.func();\n"; 
   
   private static final String FAILURE_4 =
   "class Typ {\n"+
   "   private var x;\n"+
   "}"+
   "new Typ().x;\n"; 
   
   private static final String FAILURE_5 =
   "class Typ {\n"+
   "   private static var x;\n"+
   "}"+
   "Typ.x;\n"; 
   
   private static final String FAILURE_6 =
   "class Mod {\n"+
   "   private const x;\n"+
   "}"+
   "Mod.x;\n"; 
   
   private static final String FAILURE_7 =
   "class Mod {\n"+
   "   private var x;\n"+
   "}"+
   "Mod.x;\n"; 
   
   private static final String FAILURE_8 =
   "class Typ {\n"+
   "   private static func(){}\n"+
   "}"+
   "class Typ2 {\n"+
   "   static func(){\n"+
   "      Typ.func();\n"+
   "   }\n"+
   "}"+   
   "Typ2.func();\n";   
   
   private static final String FAILURE_9 =
   "class Typ {\n"+
   "   private func(){}\n"+
   "}"+
   "class Typ2 {\n"+
   "   var f: Typ;\n"+
   "   func(){\n"+
   "      f.func();\n"+
   "   }\n"+
   "}"+   
   "new Typ2().func();\n";   
   
   public void testModificationOfConstants() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4);
      assertCompileSuccess(SUCCESS_5);
      assertCompileError(FAILURE_1, "Function 'func()' for 'default.Typ' is not accessible in /default.tern at line 3");
      assertCompileError(FAILURE_2, "Function 'func()' for 'default.Typ' is not accessible in /default.tern at line 3");
      assertCompileError(FAILURE_3, "Function 'func()' for 'default.Mod' is not accessible in /default.tern at line 3");
      assertCompileError(FAILURE_4, "Property 'x' for 'default.Typ' is not accessible in /default.tern at line 3");
      assertCompileError(FAILURE_5, "Property 'x' for 'default.Typ' is not accessible in /default.tern at line 3");
      assertCompileError(FAILURE_6, "Property 'x' for 'default.Mod' is not accessible in /default.tern at line 3");
      assertCompileError(FAILURE_7, "Property 'x' for 'default.Mod' is not accessible in /default.tern at line 3");
      assertCompileError(FAILURE_8, "Function 'func()' for 'default.Typ' is not accessible in /default.tern at line 5");
      assertCompileError(FAILURE_9, "Function 'func()' for 'default.Typ' is not accessible in /default.tern at line 6");
   }

}
