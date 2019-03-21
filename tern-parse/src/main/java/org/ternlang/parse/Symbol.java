package org.ternlang.parse;

public enum Symbol {
   IDENTIFIER(TokenType.IDENTIFIER, "identifier") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.identifier();
      }
   },
   TYPE(TokenType.TYPE, "type") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.type();
      }
   },   
   QUALIFIER(TokenType.QUALIFIER, "qualifier") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.qualifier();
      }
   },   
   HEXADECIMAL(TokenType.HEXADECIMAL, "hexadecimal") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.hexadecimal();
      }
   },   
   BINARY(TokenType.BINARY, "binary") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.binary();
      }
   },
   DECIMAL(TokenType.DECIMAL, "decimal") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.decimal();
      }
   },
   TEXT(TokenType.TEXT, "text") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.text();
      }
   },
   TEMPLATE(TokenType.TEMPLATE, "template") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.template();
      }
   }; 
   
   public final TokenType type;
   public final String name;
   
   private Symbol(TokenType type, String name) {
      this.type = type;
      this.name = name;
   }

   public abstract boolean read(TokenReader builder);
}