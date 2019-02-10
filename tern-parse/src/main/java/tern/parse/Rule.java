package tern.parse;

public class Rule {
   
   private final RuleType type;
   private final String origin;
   private final String symbol;
   
   public Rule(RuleType type, String symbol, String origin) {
      this.origin = origin;
      this.symbol = symbol;
      this.type = type;
   }           
   
   public String getOrigin(){
      return origin;
   }
   
   public String getSymbol(){
      return symbol;
   }

   public RuleType getType() {
      return type;
   }     
   
   @Override
   public String toString(){
      return symbol;
   }
}