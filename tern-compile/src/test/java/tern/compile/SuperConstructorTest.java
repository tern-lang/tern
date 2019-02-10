package tern.compile;

import tern.core.Context;

public class SuperConstructorTest extends ScriptTestCase  {

   private static final String SUCCESS_1 =
   "class Foo{\n"+
   "   private let x = 2;\n"+
   "   fun(){\n"+
   "      x++\n;"+
   "   }\n"+
   "}";      
   
   private static final String SUCCESS_2 =
   "enum Color{\n"+
   "   RED,\n"+
   "   GREEN,\n"+
   "   BLUE;\n"+
   "}\n"+
   "abstract class Shape{\n"+
   "   const id;\n"+
   "   new(id){\n"+
   "      this.id=id;\n"+
   "   }\n"+
   "   abstract draw(c: Canvas);\n"+
   "}\n"+
   "class Circle extends Shape{\n"+
   "   const color;\n"+
   "   const radius;\n"+
   "   new(id, radius):this(id, radius, Color.RED){}\n"+
   "   new(id, radius, color):super(id){\n"+
   "      this.radius=radius;\n"+
   "      this.color=color;\n"+
   "   }\n"+
   "   draw(c:Canvas){\n"+
   "      println(\"Circle ${radius} ${color}\");\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "trait Canvas{\n"+
   "}\n"+
   "const circle = new Circle('x', 11);\n"+
   "assert circle != null;\n";
   
   public static final String FAILURE_1 = 
   "class Foo{\n"+
   "   let x = 1;\n"+
   "   fun(){\n"+
   "      println(x);\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "class Bar extends Foo{\n"+
   "   let x = 'aa';\n"+
   "   override fun(){\n"+
   "      super.fun();\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "new Bar().fun();\n";
         
   
   public void testSuperConstructor() throws Exception {
      assertScriptExecutes(SUCCESS_1); // Any() default constructor
      assertScriptExecutes(SUCCESS_2);
      assertScriptExecutes(FAILURE_1, new AssertionCallback() {
         public void onSuccess(Context context, Object result) throws Exception{
            throw new IllegalStateException("Should have failed");
         }
         public void onException(Context context, Exception cause) throws Exception{
            cause.printStackTrace();
         }
      });
   }
}
