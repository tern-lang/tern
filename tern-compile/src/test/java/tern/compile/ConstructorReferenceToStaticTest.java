package tern.compile;

import tern.core.Context;

public class ConstructorReferenceToStaticTest extends ScriptTestCase {

   private static final String SUCCESS_1 =
   "class X{\n"+
   "   static const BLAH = \"ss\";\n"+
   "   var a;\n"+
   "   var b;\n"+
   "   var c;\n"+
   "   new(a,b):this(a,b,BLAH){\n"+
   "   }\n"+
   "   new(a,b,c){\n"+
   "      this.a=a;\n"+
   "      this.b=b;\n"+
   "      this.c=c;\n"+
   "   }\n"+
   "\n"+
   "   dump(){\n"+
   "      println(\"a=${a} b=${b} c=${c}\");\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class Y extends X {\n"+
   "   static const DEFAULT_SIZE = 10;\n"+
   "   var d;\n"+
   "   new(a,b) : super(a, DEFAULT_SIZE){\n"+
   "      this.d=b;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return \"a=${a}, b=${b}, c=${c}, d=${d}\";\n"+
   "   }\n"+
   "}\n"+
   "var y = new Y(1, 2);\n"+
   "println(y);\n"+
   "assert y.toString() == 'a=1, b=10, c=ss, d=2';\n";
   
   private static final String FAILURE_1 =
   "class X{\n"+
   "   static const BLAH = \"ss\";\n"+
   "   var a;\n"+
   "   var b;\n"+
   "   var c;\n"+
   "   new(a,b):this(a,b,BLAH){\n"+
   "   }\n"+
   "   new(a,b,c){\n"+
   "      this.a=a;\n"+
   "      this.b=b;\n"+
   "      this.c=c;\n"+
   "   }\n"+
   "\n"+
   "   dump(){\n"+
   "      println(\"a=${a} b=${b}\");\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class Y extends X {\n"+
   "   static const DEFAULT_SIZE = 10;\n"+
   "   var a; // <-- this VARIABLE IS A DEFINED IN THE SUPER\n"+
   "   new(a,b) : super(a, DEFAULT_SIZE){\n"+
   "      this.a=b;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return \"${a}, ${b}\";\n"+
   "   }\n"+
   "}\n"+
   "var y = new Y(1, 2);\n"+
   "println(y);\n";
   
   public void testConstructorStaticReference() throws Exception{
      assertScriptExecutes(SUCCESS_1);
      assertScriptExecutes(FAILURE_1, new AssertionCallback() {
         public void onSuccess(Context context, Object result) throws Exception{
            throw new IllegalStateException("Should have failed with: Type 'default.Y' has a duplicate property 'a'");
         }
         public void onException(Context context, Exception cause) throws Exception{
            String message = cause.getMessage();
            System.err.println(message);
            assertEquals(message, "Type 'default.Y' has a duplicate property 'a'");
         }
      });
   }
   
   public static void main(String[] list) throws Exception {
      new ConstructorReferenceToStaticTest().testConstructorStaticReference();
   }
         
}
