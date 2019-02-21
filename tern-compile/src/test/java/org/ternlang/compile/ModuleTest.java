package org.ternlang.compile;

import org.ternlang.core.Context;

public class ModuleTest extends ScriptTestCase {
   
   private static final String SOURCE_1=
   "@Blah\n"+
   "module M{\n"+
   "   class X{\n"+
   "      var i;\n"+
   "      new(i){\n"+
   "         this.i = i;\n"+
   "      }\n"+
   "      toString(){\n"+
   "         return \"\"+i;\n"+
   "      }\n"+
   "   }\n"+
   "   var x = new X(11);\n"+
   "   var y = x.class.getModule();\n"+
   "\n"+
   "   //System.err.println(this);\n"+
   "   System.err.println(x);\n"+
   "}\n";

   private static final String SOURCE_2=
   "module Mod {\n"+
   "   createTyp(i){\n"+
   "      println(Mod.getContext());\n"+
   "      return new Typ(i);\n"+
   "   }\n"+
   "}\n"+
   "class Typ{\n"+
   "   var i;\n"+
   "   new(i){\n"+
   "      this.i = i;\n"+
   "   }\n"+
   "   toString() {\n"+
   "      return \"\"+i;\n"+
   "   }\n"+
   "}\n"+
   "System.err.println(Mod.createTyp(55));\n";
   
   private static final String SOURCE_3=
   "module Mod {\n"+
   "   abstract foo(i);\n"+
   "   createTyp(i){\n"+
   "      println(Mod.getContext());\n"+
   "      return new Typ(i);\n"+
   "   }\n"+         
   "   }\n"+
   "}\n"+
   "System.err.println(Mod.foo(55));\n";

   private static final String SOURCE_4 =
   "module F {\n"+
   "\n"+
   "   @Target(values: [CLASS, PROPERTY, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER])\n"+
   "   @Retention(value: AnnotationRetention.BINARY)\n"+
   "   @MustBeDocumented\n"+
   "   async call() {\n"+
   "      return await 11;\n"+
   "   }\n"+
   "}\n"+
   "F.call().success(this::println);\n";


   public void testModuleInnerClass() throws Exception {
      assertScriptExecutes(SOURCE_1);
   }
   
   public void testModuleOuterClass() throws Exception {
      assertScriptExecutes(SOURCE_2);
   }
   
   public void testModuleWithAbstractMethod() throws Exception {
      assertScriptExecutes(SOURCE_3, new AssertionCallback() {

         @Override
         public void onSuccess(Context context, Object result) {
            assertTrue("Should fail on abstract method", false);
         }
         @Override
         public void onException(Context context, Exception result) {
            result.printStackTrace();
         }
      });
   }

   public void testModuleWithBadFunctionAnnotations() throws Exception {
      assertScriptExecutes(SOURCE_4, new AssertionCallback() {

         @Override
         public void onSuccess(Context context, Object result) {
            assertTrue("Should fail on bad annotations", false);
         }
         @Override
         public void onException(Context context, Exception result) {
            result.printStackTrace();
         }
      });
   }
}
