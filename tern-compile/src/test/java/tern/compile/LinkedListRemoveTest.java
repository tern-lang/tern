package tern.compile;

import junit.framework.TestCase;

public class LinkedListRemoveTest extends TestCase {
   
   private static final String SOURCE =
   "var list = new LinkedList();\n"+
   "list.add('a');\n"+
   "list.add('b');\n"+
   "list.add('c');\n"+
   "\n"+
   "assert list.remove(1) == 'b';\n"+
   "assert list.size() == 2;\n"+
   "assert list.remove(0) == 'a';\n"+
   "assert list.size() == 1;\n"+
   "assert list.remove(0) == 'c';\n"+
   "assert list.isEmpty();\n";

   public void testObject() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute();
   }
}
