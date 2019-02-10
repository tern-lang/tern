package tern.parse;

import junit.framework.TestCase;

public class GrammarReaderTest extends TestCase {
   
   public void testLineReader() throws Exception {
      GrammarReader reader = new GrammarReader("grammar.txt");
      for(GrammarDefinition definition : reader){
         String name = definition.getName();
         String value = definition.getDefinition();
         
         assertEquals(name, name.trim());
         System.err.println("["+name + "]=" + value);
         System.err.println();
      }
   }
}
