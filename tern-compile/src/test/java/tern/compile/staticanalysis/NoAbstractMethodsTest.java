package tern.compile.staticanalysis;

import tern.compile.ScriptTestCase;
import tern.core.Context;

public class NoAbstractMethodsTest extends ScriptTestCase {

   private static final String SOURCE_1 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "class GameListener with KeyListener {\n"+
   "\n"+
   "    // 'Y' is typed.\n"+
   "    public keyTyped(e: KeyEvent) {\n"+
   "       var key: Integer = e.getKeyCode();\n"+
   "       if (key == KeyEvent.VK_Y) {\n"+
   "          println('Y');\n"+
   "       }\n"+
   "    }\n"+
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
   
   private static final String SOURCE_2 =
   "import java.awt.event.KeyAdapter;\n"+
   "class Foo extends KeyAdapter{\n"+
   "   foo(){\n"+
   "      return true;\n"+
   "   }\n"+
   "}\n";    
   
   private static final String SOURCE_3 =
   "import java.awt.event.KeyListener;\n"+
   "class Foo with KeyListener{\n"+
   "   foo(){\n"+
   "      return true;\n"+
   "   }\n"+
   "}\n";       
   
   private static final String SOURCE_4 = 
   "import awt.event.KeyListener;\n"+
   "import awt.event.KeyEvent;\n"+
   "\n"+
   "class GameListener with KeyListener {\n"+
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
   "}\n"; 
   
   public void testAllMethodsImplementedSuccess() throws Exception {
      assertScriptExecutes(SOURCE_1);
   }
   
   public void testAllMethodsImplementedBySuperTypeSuccess() throws Exception {
      assertScriptExecutes(SOURCE_2);
   }
   
   public void testNoMethodsImplemented() throws Exception {
      assertScriptExecutes(SOURCE_3, new AssertionCallback() {
         public void onSuccess(Context context, Object result){
            assertTrue("Should have failed due to abstract methods", false);
         }
         @Override
         public void onException(Context context, Exception cause) {
            cause.printStackTrace();
         }
      });
   }
   
   public void testSomeMethodsImplemented() throws Exception {
      assertScriptExecutes(SOURCE_4, new AssertionCallback() {
         public void onSuccess(Context context, Object result){
            assertTrue("Should have failed due to abstract methods", false);
         }
         @Override
         public void onException(Context context, Exception cause) {
            cause.printStackTrace();
         }
      });
   }
}
