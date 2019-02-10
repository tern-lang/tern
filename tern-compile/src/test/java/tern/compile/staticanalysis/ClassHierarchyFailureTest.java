package tern.compile.staticanalysis;

public class ClassHierarchyFailureTest extends CompileTestCase {
   
   private static final String SUCCESS_1 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "abstract class GameListener with KeyListener {}\n";
   
   private static final String SUCCESS_2 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "abstract class GameListener with KeyListener {\n"+
   "\n"+
   "    public keyReleased(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "\n"+
   "       // 'Z' is pressed.\n"+
   "        if (key == KeyEvent.VK_Z) { // pause\n"+
   "            println('Z');\n"+
   "        }\n"+
   "        // 'X' is pressed.\n"+
   "        if (key == KeyEvent.VK_X) { // resume\n"+
   "            println('X');\n"+
   "        }\n"+
   "        // '1' is pressed.\n"+
   "        if (key == KeyEvent.VK_1) {\n"+
   "            println('1');\n"+
   "        }\n"+
   "    }\n"+
   "\n"+
   "    // 'L' is pressed or held.\n"+
   "    public keyPressed(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "       if (key == KeyEvent.VK_L) {\n"+
   "          println('L');\n"+
   "       }\n"+
   "    }\n"+
   "\n"+
   "}\n";
   
   private static final String SUCCESS_3 = 
   "abstract class GameListener {}\n";
   
   private static final String FAILURE_1 = 
   "class X{\n"+
   "   new(a, b){}\n"+
   "}\n"+
   "class Y extends X{\n"+
   "   new(a){}\n"+
   "}\n";       

   private static final String FAILURE_2 = 
   "class X{\n"+
   "   new(a, b){}\n"+
   "}\n"+
   "class Y extends X {\n"+
   "   new(a): super(a){}\n"+
   "}\n";
 
   private static final String FAILURE_3 =
   "class X{\n"+
   "   new(a: String, b: Date){}\n"+
   "}\n"+
   "class Y extends X{\n"+
   "   new(a): super('a', 11){}\n"+
   "}\n";
   
   private static final String FAILURE_4 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "class GameListener extends KeyListener {\n"+
   "\n"+
   "    public keyReleased(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "\n"+
   "       // 'Z' is pressed.\n"+
   "        if (key == KeyEvent.VK_Z) { // pause\n"+
   "            println('Z');\n"+
   "        }\n"+
   "        // 'X' is pressed.\n"+
   "        if (key == KeyEvent.VK_X) { // resume\n"+
   "            println('X');\n"+
   "        }\n"+
   "        // '1' is pressed.\n"+
   "        if (key == KeyEvent.VK_1) {\n"+
   "            println('1');\n"+
   "        }\n"+
   "    }\n"+
   "\n"+
   "    // 'L' is pressed or held.\n"+
   "    public keyPressed(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "       if (key == KeyEvent.VK_L) {\n"+
   "          println('L');\n"+
   "       }\n"+
   "    }\n"+
   "\n"+
   "}\n";
   
   private static final String FAILURE_5 = 
   "import awt.event.KeyAdapter;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "class GameListener with KeyAdapter {\n"+
   "\n"+
   "    public keyReleased(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "\n"+
   "       // 'Z' is pressed.\n"+
   "        if (key == KeyEvent.VK_Z) { // pause\n"+
   "            println('Z');\n"+
   "        }\n"+
   "        // 'X' is pressed.\n"+
   "        if (key == KeyEvent.VK_X) { // resume\n"+
   "            println('X');\n"+
   "        }\n"+
   "        // '1' is pressed.\n"+
   "        if (key == KeyEvent.VK_1) {\n"+
   "            println('1');\n"+
   "        }\n"+
   "    }\n"+
   "\n"+
   "    // 'L' is pressed or held.\n"+
   "    public keyPressed(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "       if (key == KeyEvent.VK_L) {\n"+
   "          println('L');\n"+
   "       }\n"+
   "    }\n"+
   "\n"+
   "}\n";
   
   public void testHierarchyCompilation() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);         
      assertCompileError(FAILURE_1, "Constructor 'new()' not found for 'default.X' in /default.tern at line 4");
      assertCompileError(FAILURE_2, "Constructor 'new(default.Any)' not found for 'default.X' in /default.tern at line 4");
      assertCompileError(FAILURE_3, "Constructor 'new(lang.String, lang.Integer)' not found for 'default.X' in /default.tern at line 4");
      assertCompileError(FAILURE_4, "Invalid super class 'awt.event.KeyListener' for type 'default.GameListener' in /default.tern at line 4");
      assertCompileError(FAILURE_5, "Invalid trait 'awt.event.KeyAdapter' for type 'default.GameListener' in /default.tern at line 4");
   }

}
