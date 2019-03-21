package org.ternlang.parse;

public abstract class TokenConsumer implements TokenReader {

   protected TokenLexer lexer;
   protected Token value;

   protected TokenConsumer() {
      this(null);
   }      
   
   protected TokenConsumer(TokenLexer lexer) {
      this.lexer = lexer;
   }
   
   @Override
   public boolean literal(String text) {
      Token token = lexer.literal(text);

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }   

   @Override
   public boolean decimal() {
      Token token = lexer.decimal();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean binary() {
      Token token = lexer.binary();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }

   @Override
   public boolean hexadecimal() {
      Token token = lexer.hexadecimal();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }

   @Override
   public boolean identifier() {
      Token token = lexer.identifier();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean qualifier() {
      Token token = lexer.qualifier();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }     
   
   @Override
   public boolean type() {
      Token token = lexer.type();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }       

   @Override
   public boolean text() {
      Token token = lexer.text();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean template() {
      Token token = lexer.template();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean space() {
      Token token = lexer.space();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
}