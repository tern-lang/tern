package org.ternlang.compile.staticanalysis;

public class IllegalConstructorCallTest extends CompileTestCase {
   
   private static final String SUCCESS_1 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "abstract class GameListener with KeyListener {\n"+
   "\n"+
   "    new(val){}\n"+
   "\n"+
   "    // 'Y' is typed.\n"+
   "    public keyTyped(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "       if (key == KeyEvent.VK_Y) {\n"+
   "          println('Y');\n"+
   "       }\n"+
   "    }\n"+
   "}\n"+   
   "class GameController extends GameListener {\n"+
   "\n"+
   "    new(x): super(x){}\n"+ // can constructor of an abstract class
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
   "}\n"+
   "new GameController(11);\n";
   
   private static final String SUCCESS_2 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "abstract class GameListener with KeyListener {\n"+
   "\n"+   
   "    // 'Y' is typed.\n"+
   "    public keyTyped(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "       if (key == KeyEvent.VK_Y) {\n"+
   "          println('Y');\n"+
   "       }\n"+
   "    }\n"+   
   "}\n"+
   "class GameController extends GameListener {\n"+  
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
   "}\n"+
   "new GameController();\n";
   
   private static final String FAILURE_1 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "abstract class GameListener with KeyListener {}\n"+   
   "new GameListener();\n";
   
   private static final String FAILURE_2 = 
   "module GameLoader {}\n"+   
   "new GameLoader();\n";
   
   private static final String FAILURE_3 = 
   "enum GameDifficulty {\n"+
   "   EASY(1),\n"+
   "   MEDIUM(2),\n"+
   "   HARD(3);\n"+
   "   const level;\n"+
   "\n"+
   "   new(level){\n"+
   "      this.level = level;\n"+
   "   }\n"+
   "}\n"+
   "new GameDifficulty(1);\n";   
   
   private static final String FAILURE_4 = 
   "trait GameEnemy {\n"+
   "  battle(x) {\n"+
   "     return Math.random();\n"+   
   "  }"+   
   "}\n"+
   "new GameEnemy();\n";   
   
   private static final String FAILURE_5 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "abstract class GameListener with KeyListener {\n"+
   "\n"+
   "    new(val){}\n"+  
   "}\n"+   
   "class GameController extends GameListener {\n"+ // not default constructor
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
   "}\n"+
   "new GameController();\n";

   private static final String FAILURE_6 =
    "import awt.event.KeyListener;\n"+
    "import awt.event.KeyEvent;\n"+
    "\n"+
    "abstract class GameListener with KeyListener {\n"+
    "\n"+
    "    // 'Y' is typed.\n"+
    "    public keyTyped(e: KeyEvent) {\n"+
    "       var key: Integer = e.getKeyCode();\n"+
    "       if (key == KeyEvent.VK_Y) {\n"+
    "          println('Y');\n"+
    "       }\n"+
    "    }\n"+
    "}\n"+
    "class GameController extends GameListener {\n"+
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
    "}\n"+
    "new GameController(11);\n";

   public void testConstructorCalls() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);         
      assertCompileError(FAILURE_1, "Type 'default.GameListener' is not a concrete class in /default.tern at line 5");
      assertCompileError(FAILURE_2, "Type 'default.GameLoader' is not a concrete class in /default.tern at line 2");
      assertCompileError(FAILURE_3, "Type 'default.GameDifficulty' is not a concrete class in /default.tern at line 11");
      assertCompileError(FAILURE_4, "Type 'default.GameEnemy' is not a concrete class in /default.tern at line 5");
      assertCompileError(FAILURE_5, "Constructor 'new()' not found for 'default.GameListener' in /default.tern at line 8");
      assertCompileError(FAILURE_6, "Constructor 'new(lang.Integer)' not found for 'default.GameController' in /default.tern at line 42");
   }

}
