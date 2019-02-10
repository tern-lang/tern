package tern.parse;

import static tern.parse.TokenType.LITERAL;

public class TokenMerger {
   
   private static final int[] SCORE = {
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};     
   
   public TokenMerger() {
      super();
   }
   
   public Token merge(Token current, Token update){
      if(current != null) {
         String text = combine(current, update);
         
         if(text!= null) {
            Line line = current.getLine();
            short type = current.getType();
            
            return new StringToken(text, line, type);
         }
      }
      return update;
   }
   
   private String combine(Token current, Token update){
      short type = current.getType();
      
      if(type == LITERAL.mask) {
         String prefix = current.toString();
         String suffix = update.toString();
      
         return combine(prefix, suffix);
      }
      return null;
   }
   
   private String combine(String prefix, String suffix){
      int length = prefix.length();
      char last = prefix.charAt(length -1);
      char first = suffix.charAt(0);

      if(first < SCORE.length && last < SCORE.length) {
         int value = SCORE[first] + SCORE[last];
         
         if(value >= 2) {
            return prefix.concat(suffix);
         }
      }
      return null;
   }
}
