package tern.parse;

public class SymbolGrammar implements Grammar {

   private final GrammarMatcher matcher;
   
   public SymbolGrammar(Symbol symbol, String value, int index) {
      this.matcher = new SymbolMatcher(symbol, value, index);
   }

   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      return matcher;
   }  
   
   private static class SymbolMatcher implements GrammarMatcher {
      
      private final TokenType type;
      private final Symbol symbol;
      private final String value;
      private final int index;
      
      public SymbolMatcher(Symbol symbol, String value, int index) {
         this.type = symbol.type;
         this.symbol = symbol;
         this.value = value; 
         this.index = index;
      }
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {
         int mask = checker.peek();
         
         if((mask & type.mask) != 0) {
            int mark = checker.mark(index);
      
            if(symbol.read(checker)) {
               checker.commit(mark, index);
               return true;
            }
         }
         return false;
      }
   
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {
         int mask = builder.peek();
         
         if((mask & type.mask) != 0) {
            SyntaxBuilder child = builder.mark(index);
      
            if(symbol.read(child)) {
               child.commit();
               return true;
            }
         }
         return false;
      }
      
      @Override
      public String toString() {
         return String.format("[%s]", value);
      } 
   }
}