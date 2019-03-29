package org.ternlang.compile.staticanalysis;

import org.ternlang.core.Bug;

public class ConstantModificationCompileTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "const list = [1,2,3,4];\n"+
   "list[1] = 10;\n";   

   private static final String SUCCESS_2 =
   "class Typ {\n"+
   "   const x;\n"+      
   "   new(a, b){\n"+
   "      this.x=a;;\n"+
   "   }\n"+
   "}";    
   
   private static final String SUCCESS_3 =
   "class Typ {\n"+
   "   const x;\n"+
   "   const y=11;\n"+            
   "   new(a, b){\n"+
   "      x=a;\n"+
   "   }\n"+
   "}";   
   
   private static final String SUCCESS_4 =
   "class Typ {\n"+
   "   static const VAR=10;\n"+
   "   const y=-VAR;\n"+  
   "   var x;\n"+     
   "   new(a, b){\n"+
   "      x=a;\n"+
   "   }\n"+
   "}";    
   
   private static final String SUCCESS_5 =
   "class Typ {\n"+
   "   static const VAR=10;\n"+
   "   const y=+VAR;\n"+            
   "   var x;\n"+     
   "   new(a, b){\n"+
   "      x=!VAR;\n"+
   "   }\n"+
   "}";    
   
   private static final String SUCCESS_6 =
   "class Typ {\n"+
   "   static const VAR=10;\n"+
   "   const y=+VAR;\n"+            
   "   var x;\n"+     
   "   new(a, b){\n"+
   "      x=~VAR;\n"+
   "   }\n"+
   "}";    
   
   private static final String FAILURE_1 =
   "const x = 11;\n"+
   "x = 12;\n";
   
   private static final String FAILURE_2 =
   "module Mod{}\n"+
   "Mod = 12;\n";
   
   private static final String FAILURE_3 = 
   "Integer.MAX_VALUE = 12;\n";
   
   private static final String FAILURE_4 = 
   "function fun(const a, var b){\n"+
   "   b=11;\n"+
   "   a=12;\n"+
   "}\n";

   private static final String FAILURE_5 = 
   "module Mod {\n"+
   "   fun(const a, var b){\n"+
   "      b=11;\n"+
   "      a=12;\n"+
   "   }\n"+
   "}";

   private static final String FAILURE_6 = 
   "class Typ {\n"+
   "   fun(const a, var b){\n"+
   "      b=11;\n"+
   "      a=12;\n"+
   "   }\n"+
   "}";

   private static final String FAILURE_7 = 
   "module Mod {\n"+
   "   const x =11;\n"+
   "   fun(a, b){\n"+
   "      x=22;\n"+
   "   }\n"+
   "}";
   
   private static final String FAILURE_8 = 
   "class Typ {\n"+
   "   const x =11;\n"+
   "   fun(a, b){\n"+
   "      x=22;\n"+
   "   }\n"+
   "}";
   
   private static final String FAILURE_9 = 
   "class Typ {\n"+
   "   static const x =11;\n"+
   "   fun(a, b){\n"+
   "      x=22;\n"+
   "   }\n"+
   "}";
   
   private static final String FAILURE_10 = 
   "class Typ {\n"+
   "   fun(a, b){\n"+
   "      this=11;\n"+
   "   }\n"+
   "}";
   
   private static final String FAILURE_11 = 
   "class Typ {\n"+
   "   fun(a, b){\n"+
   "      class=11;\n"+
   "   }\n"+
   "}";
   
   private static final String FAILURE_12 = 
   "class Typ {\n"+
   "   fun(a, b){\n"+
   "      super=11;\n"+
   "   }\n"+
   "}";     
   
   private static final String FAILURE_13 = 
   "class Typ {\n"+
   "   const x =11;\n"+
   "   fun(a, b){\n"+
   "      x-=10;\n"+
   "   }\n"+
   "}";
   
   private static final String FAILURE_14 = 
   "class Typ {\n"+
   "   const x =11;\n"+
   "   fun(a, b){\n"+
   "      x++;\n"+
   "   }\n"+
   "}";  
   
   
   private static final String FAILURE_15 = 
   "class Typ {\n"+
   "   const x =11;\n"+
   "   fun(a, b){\n"+
   "      ++x;\n"+
   "   }\n"+
   "}";   
      
   private static final String FAILURE_16 = 
   "class Typ {\n"+
   "   const x =11;\n"+
   "   fun(a, b){\n"+
   "      --x;\n"+
   "   }\n"+
   "}";   
         
   private static final String FAILURE_17 = 
   "enum Color {\n"+
   "   RED,\n"+
   "   GREEN,\n"+
   "   BLUE;\n"+   
   "   fun(a, b){\n"+
   "      BLUE=10;\n"+
   "   }\n"+
   "}";  
   
   private static final String FAILURE_18 = 
   "enum Color {\n"+
   "   RED,\n"+
   "   GREEN,\n"+
   "   BLUE;\n"+   
   "   fun(a, b){\n"+
   "      BLUE.name=10;\n"+
   "   }\n"+
   "}";  
   
   private static final String FAILURE_19 = 
   "enum Color {\n"+
   "   RED,\n"+
   "   GREEN,\n"+
   "   BLUE;\n"+   
   "   fun(a, b){\n"+
   "      BLUE.ordinal=10;\n"+
   "   }\n"+
   "}";  
   
   private static final String FAILURE_20 = 
   "enum Color {\n"+
   "   RED,\n"+
   "   GREEN,\n"+
   "   BLUE;\n"+   
   "   fun(a, b){\n"+
   "      values=10;\n"+
   "   }\n"+
   "}";  
   
   private static final String FAILURE_21 = 
   "enum Color {\n"+
   "   RED,\n"+
   "   GREEN,\n"+
   "   BLUE;\n"+   
   "   fun(a, b){\n"+
   "      return 0;\n"+
   "   }\n"+
   "}\n"+
   "Color.RED.name=10;";
   
   private static final String FAILURE_22 = 
   "enum Color {\n"+
   "   RED,\n"+
   "   GREEN,\n"+
   "   BLUE;\n"+   
   "   fun(a, b){\n"+
   "      return 0;\n"+
   "   }\n"+
   "}\n"+
   "Color.RED.ordinal=10;";   
   
   private static final String FAILURE_23 = 
   "enum Color {\n"+
   "   RED,\n"+
   "   GREEN,\n"+
   "   BLUE;\n"+   
   "   fun(a, b){\n"+
   "      return 0;\n"+
   "   }\n"+
   "}\n"+
   "Color.values=10;";   
   
   @Bug("SUCCESS_3 should pass but it fails")
   public void testModificationOfConstants() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      //assertCompileSuccess(SUCCESS_3); 
      assertCompileSuccess(SUCCESS_4); 
      assertCompileSuccess(SUCCESS_5); 
      assertCompileSuccess(SUCCESS_6);
      assertCompileError(FAILURE_1, "Illegal modification of constant in /default.tern at line 2");
      assertCompileError(FAILURE_2, "Illegal modification of constant in /default.tern at line 2");
      assertCompileError(FAILURE_3, "Illegal modification of constant in /default.tern at line 1");
      assertCompileError(FAILURE_4, "Illegal modification of constant in /default.tern at line 3");
      assertCompileError(FAILURE_5, "Illegal modification of constant in /default.tern at line 4");
      assertCompileError(FAILURE_6, "Illegal modification of constant in /default.tern at line 4");
      assertCompileError(FAILURE_7, "Illegal modification of constant in /default.tern at line 4");
      assertCompileError(FAILURE_8, "Illegal modification of constant in /default.tern at line 4");
      assertCompileError(FAILURE_9, "Illegal modification of constant in /default.tern at line 4");
      assertCompileError(FAILURE_10, "Illegal modification of constant in /default.tern at line 3");
      assertCompileError(FAILURE_11, "Illegal modification of constant in /default.tern at line 3");
      assertCompileError(FAILURE_12, "Illegal modification of constant in /default.tern at line 3");
      assertCompileError(FAILURE_13, "Illegal modification of constant in /default.tern at line 4");
      assertCompileError(FAILURE_14, "Illegal ++ of constant in /default.tern at line 4");
      assertCompileError(FAILURE_15, "Illegal ++ of constant in /default.tern at line 4");
      assertCompileError(FAILURE_16, "Illegal -- of constant in /default.tern at line 4");
      assertCompileError(FAILURE_17, "Illegal modification of constant in /default.tern at line 6");
      assertCompileError(FAILURE_18, "Illegal modification of constant in /default.tern at line 6");
      assertCompileError(FAILURE_19, "Illegal modification of constant in /default.tern at line 6");
      assertCompileError(FAILURE_20, "Illegal modification of constant in /default.tern at line 6");
      assertCompileError(FAILURE_21, "Illegal modification of constant in /default.tern at line 9");
      assertCompileError(FAILURE_22, "Illegal modification of constant in /default.tern at line 9");
      assertCompileError(FAILURE_23, "Illegal modification of constant in /default.tern at line 9");
   }

}
