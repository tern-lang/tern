package tern.compile;

import tern.compile.staticanalysis.CompileTestCase;
import tern.core.Bug;

public class DuplicatePropertyTest extends CompileTestCase {

   private static final String SUCCESS_1 = 
   "class Foo {\n"+
   "   private let x = 1;\n"+
   "}\n"+
   "class Bar extends Foo {\n"+
   "   let x = 11;\n"+
   "}\n";
   
   private static final String SUCCESS_2 = 
   "class Foo {\n"+
   "   private let x = 1;\n"+
   "   getName(){\n"+
   "     return 'Foo';\n"+
   "   }\n"+
   "}\n"+
   "class Bar extends Foo {\n"+
   "   let x = 11;\n"+
   "   getName(){\n"+
   "     return 'Bar';\n"+
   "   }\n"+   
   "}\n"+
   "println(new Bar().name);\n"+
   "assert new Bar().name == 'Bar';";
   
   private static final String SUCCESS_3 = 
   "import swing.JFrame;\n" +         
   "class Game extends JFrame {\n"+
   "   private height = 1;\n"+
   "}\n";
   
   private static final String FAILURE_1 = 
   "class Foo {\n"+
   "   let x = 1;\n"+
   "}\n"+
   "class Bar extends Foo {\n"+
   "   let x = 11;\n"+
   "}\n";
   
   public void testDuplicateProperty() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileAndExecuteSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileError(FAILURE_1, "Type 'default.Bar' has a duplicate property 'x' in /default.snap at line 4");
   }
         
}
