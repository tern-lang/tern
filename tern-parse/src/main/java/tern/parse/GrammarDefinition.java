package tern.parse;

public class GrammarDefinition {

   private final String definition;
   private final String name;
   
   public GrammarDefinition(String name, String definition) {
      this.definition = definition;
      this.name = name;
   }
   
   public String getName() {
      return name;
   }
   
   public String getDefinition() {
      return definition;
   }
}