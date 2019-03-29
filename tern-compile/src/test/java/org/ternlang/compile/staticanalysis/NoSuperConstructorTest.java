package org.ternlang.compile.staticanalysis;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.ternlang.compile.ScriptTestCase;
import org.ternlang.core.Context;

public class NoSuperConstructorTest extends ScriptTestCase {
   
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
   "class Bar extends Foo{\n"+
   "   var text;\n"+
   "   new(text){\n"+
   "      this.text=text;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Bar(${text})\";\n"+
   "   }\n"+
   "}\n";
         
   private static final String SOURCE_3 =
   "class Demo extends Bar{\n"+
   "   var foo;\n"+
   "   new(x,y): super(x){\n"+
   "      this.foo = new Foo(x,y);\n"+
   "   }\n"+
   "   get(): Foo{\n"+
   "      return foo;\n"+
   "   }\n"+
   "}";

   private static final String SOURCE_4 =
   "func main(){ \n" +      
   "  var demo = new Demo(\"x\",33);\n"+
   "  var bar = new Bar(\"test\");\n"+
   "  var foo = demo.get();\n"+
   "  \n"+
   "  println(bar);\n"+
   "  println(foo);\n"+ 
   "}";
   
   public void testMissingSuperConstructor() throws Exception {
      addScript("/com/test/foo/Foo.tern", SOURCE_1);
      addScript("/com/test/foo/Bar.tern", SOURCE_2);
      addScript("/com/test/foo/Demo.tern", SOURCE_3);
      addScript("/com/test/foo.tern", SOURCE_4);
      assertExpressionEvaluates("/com/test/foo.tern", "main()", "com.test.foo", new AssertionCallback() {
         public void onSuccess(Context context, Object result) throws Exception{
            throw new IllegalStateException("Missing constructor");
         }
         public void onException(Context context, Exception cause) throws Exception{
//            CHRIST KNOWS WHAT IS GOING ON HERE            
//            StringWriter writer = new StringWriter();
//            PrintWriter printer = new PrintWriter(writer);
//            
//            cause.printStackTrace(printer);
//            printer.flush();
//            
//            String reality = writer.toString();
//            assertEquals(reality, "Constructor 'new()' not found for 'com.test.foo.Foo'");
         }
      });
   }
}
