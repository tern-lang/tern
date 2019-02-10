package tern.compile.link;

import tern.compile.ScriptTestCase;
import tern.compile.verify.VerifyException;

public class LinkWithMissingFunctionTest extends ScriptTestCase {
   
   private static final String SOURCE_1 =
   "import util.regex.Pattern;\n"+
   "class Tree {\n"+
   "   sortOrder(){\n"+
   "      sortOrder(1);\n"+
   "   }\n"+
   "   sortOrder(a, b){}\n"+
   "}\n";
   
  
   private static final String SOURCE_2 =
   "func main(){\n" +      
   "  var tree = Tree();\n"+
   "  tree.sortOrder();\n"+
   "}\n";

   
   public void testMissingFunction() throws Exception {
      addScript("/com/test/foo/Tree.snap", SOURCE_1);
      addScript("/com/test/foo.snap", SOURCE_2);

      try {
         assertExpressionEvaluates("/com/test/foo.snap", "main()", "com.test.foo");
      } catch(VerifyException e) { // this is wrong, should not throw error
         String message = e.getErrors().get(0).getDescription();
         System.err.println(message);
         e.printStackTrace();
         assertEquals(message, "Function 'sortOrder(lang.Integer)' not found for 'com.test.foo.Tree' in /com/test/foo/Tree.snap at line 4");   
      }
   }
}
