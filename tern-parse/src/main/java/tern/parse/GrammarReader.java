package tern.parse;

import tern.common.io.PropertyReader;

public class GrammarReader extends PropertyReader<GrammarDefinition> {
   
   public GrammarReader(String file) {
      super(file);
   }
   
   @Override
   protected boolean terminal(char value) {
      return value == ';';
   }

   @Override
   protected GrammarDefinition create(String name, char[] data, int off, int length, int line) {
      String value = new String(data, off, length);
      GrammarDefinition definition = new GrammarDefinition(name, value);
      
      return definition;
   }
}