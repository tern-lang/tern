package tern.compile.link;

import tern.compile.ScriptTestCase;
import tern.core.Context;
import tern.core.scope.EmptyModel;

public class TypeLookupPerSecondTest extends ScriptTestCase {
   
   private static final int ITERATIONS = 10 * 1000000;

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
   "class Bar{\n"+
   "   var text;\n"+
   "   new(text){\n"+
   "      this.text=text;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Bar(${text})\";\n"+
   "   }\n"+
   "}\n";         
   
   private static final String SOURCE_3 =
   "func main(){\n" +      
   "  var bar = new Bar(\"test\");\n"+
   "  var foo = new Foo(\"x\",33);\n"+
   "\n"+
   "  println(bar);\n"+
   "  println(foo);\n"+
   "}\n";
   
   
   
   public void testTypeLookupPerformance() throws Exception {
      addScript("/com/test/foo/Foo.tern", SOURCE_1);
      addScript("/com/test/foo/Bar.tern", SOURCE_2);
      addScript("/com/test/foo.tern", SOURCE_3);
      getContext().getCompiler().compile("/com/test/foo.tern").execute(new EmptyModel(), true);
      Object foo = getContext().getContext().getEvaluator().evaluate(new EmptyModel(), "new Foo(1,2)", "com.test.foo");
      Object bar = getContext().getContext().getEvaluator().evaluate(new EmptyModel(), "new Bar(`22`)", "com.test.foo");
      Object str = new String();
      Object integer = new Integer(1);
      
      assertNotNull(foo);
      assertNotNull(bar);      
      
      Context context = getContext().getContext();
      System.err.println(context.getExtractor().getType(foo));
      System.err.println(context.getExtractor().getType(bar));
      System.err.println(context.getExtractor().getType(str));
      System.err.println(context.getExtractor().getType(integer));
      long start = System.currentTimeMillis();
      for(int i = 0; i < ITERATIONS; i++) {
         assertNotNull(context.getExtractor().getType(foo));
         assertNotNull(context.getExtractor().getType(bar));
         assertNotNull(context.getExtractor().getType(str));
         assertNotNull(context.getExtractor().getType(integer));
      }
      System.err.println(System.currentTimeMillis()-start);


   }
}
