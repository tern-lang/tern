package org.ternlang.parse;

import static org.ternlang.parse.TokenType.BINARY;
import static org.ternlang.parse.TokenType.DECIMAL;
import static org.ternlang.parse.TokenType.HEXADECIMAL;
import static org.ternlang.parse.TokenType.IDENTIFIER;
import static org.ternlang.parse.TokenType.LITERAL;
import static org.ternlang.parse.TokenType.QUALIFIER;
import static org.ternlang.parse.TokenType.SPACE;
import static org.ternlang.parse.TokenType.TEMPLATE;
import static org.ternlang.parse.TokenType.TEXT;
import static org.ternlang.parse.TokenType.TYPE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenIndexer {

   private final List<LiteralValue> values;
   private final LineExtractor extractor;
   private final GrammarIndexer indexer;
   private final TextReader reader;
   private final short[] lines;

   public TokenIndexer(GrammarIndexer indexer, String resource, char[] original, char[] source, short[] lines, short[] types, int count) {
      this.extractor = new LineExtractor(resource, original, count);
      this.reader = new TextReader(source, types);
      this.values = new ArrayList<LiteralValue>();
      this.indexer = indexer;
      this.lines = lines;
   }

   public short[] index(List<Token> tokens) {
      if(values.isEmpty()) {
         List<String> literals = indexer.list();
        
         for(String literal : literals) {
            LiteralValue value = new LiteralValue(literal);
           
            if(!value.isEmpty()) {
               values.add(value);
            }
         }
         Collections.sort(values);
      }
      return scan(tokens);
   }
   
   private short[] scan(List<Token> tokens) {
      int count = reader.count();

      while (true) {
         int mark = reader.mark();
         
         if(mark >= count) {
            return create(tokens);
         }
         int line = lines[mark];
         Token token = literal(line);
         
         if (token == null) {
            token = space(line);
         }
         if (token == null) {
            token = template(line);
         }
         if (token == null) {
            token = text(line);
         }
         if(token == null) {
            token = type(line);
         }
         if(token == null) {
            token = identifier(line);
         }
         if(token == null) {
            token = binary(line);
         }
         if(token == null) {
            token = hexadecimal(line);
         }
         if(token == null) {
            token = decimal(line);
         }
         if(token == null) {
            throw new ParseException("Could not parse token at line " + lines[mark]);
         } 
         tokens.add(token);
      }
   }
   
   private short[] create(List<Token> tokens) {
      int length = tokens.size();
      
      if(length > 0) {
         short[] masks = new short[length];
         
         for(int i = 0; i < length; i++) {
            Token token = tokens.get(i);
            
            if(token != null) {
               masks[i] = token.getType();
            }
         }
         return masks;
      }
      return new short[]{};
   }
   
   private Token type(int number) {
      Line line = extractor.extract(number);
      String token = reader.type();

      if (token != null) {
         return new StringToken(token, line, TYPE.mask | QUALIFIER.mask | IDENTIFIER.mask);
      }
      return null;
   }

   private Token identifier(int number) {
      Line line = extractor.extract(number);
      String token = reader.identifier();
      
      if (token != null) {
         return new StringToken(token, line, IDENTIFIER.mask | QUALIFIER.mask);
      }
      return null;
   }

   private Token decimal(int number) {
      Line line = extractor.extract(number);
      Number token = reader.decimal();

      if (token != null) {
         return new NumberToken(token, line, DECIMAL.mask);
      }
      return null;
   }
   
   private Token binary(int number) {
      Line line = extractor.extract(number);
      Number token = reader.binary();
      
      if (token != null) {
         return new NumberToken(token, line, BINARY.mask | DECIMAL.mask);
      }
      return null;
   }

   private Token hexadecimal(int number) {
      Line line = extractor.extract(number);
      Number token = reader.hexadecimal();
      
      if (token != null) {
         return new NumberToken(token, line, HEXADECIMAL.mask | DECIMAL.mask);
      }
      return null;
   }

   private Token template(int number) {
      Line line = extractor.extract(number);
      String token = reader.template();
      
      if (token != null) {
         return new StringToken(token, line, TEMPLATE.mask);
      }
      return null;
   }
   
   private Token text(int number) {
      Line line = extractor.extract(number);
      String token = reader.text();
      
      if (token != null) {
         return new StringToken(token, line, TEXT.mask);
      }
      return null;
   }
   
   private Token space(int number) {
      Line line = extractor.extract(number);
      Character token = reader.space();
      
      if (token != null) {
         return new CharacterToken(token, line, SPACE.mask);
      }
      return null;
   }
   
   private Token literal(int number) {
      Line line = extractor.extract(number);
      int count = values.size();
      
      for (int i = 0; i < count; i++) {
         int mark = reader.mark();
         LiteralValue literal = values.get(i);
         
         if (reader.literal(literal.text)) {
            char last = literal.text[literal.text.length - 1];
            char peek = reader.peek();
            
            if (identifier(last) && identifier(peek)) {
               reader.reset(mark);
            } else {
               if(identifier(last) && special(peek)) {
                  return new StringToken(literal.value, line, LITERAL.mask | IDENTIFIER.mask | QUALIFIER.mask);
               }
               return new StringToken(literal.value, line, LITERAL.mask);
            }
         }
      }
      return null;
   }
   
   private boolean identifier(char value) {
      if(value >= 'a' && value <='z') {
         return true;
      }
      if(value >= 'A' && value <= 'Z') {
         return true;
      }
      return value >= '0' && value <= '9';
   }
   
   private boolean special(char value) {
      switch(value) {
      case ')': case '(':
      case '.': case '?':
      case ',': case '=':
      case ';': case ':':
      case '+':
         return true;
      }
      return false;
   }
   
   private static class LiteralValue implements Comparable<LiteralValue> {
      
      private final String value;
      private final char[] text;

      private LiteralValue(String value) {
         this.text = value.toCharArray();
         this.value = value;
      }

      @Override
      public int compareTo(LiteralValue other) {
         if(text.length < other.text.length) {
            return 1;
         }
         if(text.length == other.text.length) {
            return 0;
         }
         return -1; 
      }
      
      public boolean isEmpty() {
         return text.length == 0;
      }
      
      @Override
      public String toString() {
         return value;
      }
   }
}