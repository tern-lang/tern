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
import java.util.List;

public class TokenScanner implements TokenLexer {

   private TokenIndexer indexer;
   private List<Token> tokens;
   private short[] masks;
   private int count;
   private int mark;

   public TokenScanner(GrammarIndexer indexer, String resource, char[] original, char[] source, short[] lines, short[] types, int count) {
      this.indexer = new TokenIndexer(indexer, resource, original, source, lines, types, count);
      this.tokens = new ArrayList<Token>();
      this.count = source.length;
   }

   @Override
   public Token<String> text() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & TEXT.mask) != 0) {
            return tokens.get(mark++);
         }
      }
      return null;
   }
   
   @Override
   public Token<String> template() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & TEMPLATE.mask) != 0) {
            return tokens.get(mark++);
         }
      }
      return null;
   }

   @Override
   public Token<String> type() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & TYPE.mask) != 0) {
            return tokens.get(mark++);
         }
      }
      return null;
   }

   @Override
   public Token<String> identifier() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & IDENTIFIER.mask) != 0) {
            return tokens.get(mark++);
         }
      }
      return null;
   }

   @Override
   public Token<String> qualifier() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & QUALIFIER.mask) != 0) {
            return tokens.get(mark++);
         }
      }
      return null;
   }
   
   @Override
   public Token<Character> space() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & SPACE.mask) != 0) {
            return tokens.get(mark++);
         }
      }
      return null;
   }

   @Override
   public Token<String> literal(String text) {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & LITERAL.mask) != 0) {
            Token token = tokens.get(mark);
            Object value = token.getValue();

            if (value == text) {
               mark++;
               return token;
            }
         }
      }
      return null;
   }
   
   @Override
   public Token<Number> binary() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & BINARY.mask) != 0) {
            return tokens.get(mark++);
         }
      }
      return null;
   }

   @Override
   public Token<Number> hexadecimal() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & HEXADECIMAL.mask) != 0) {
            return tokens.get(mark++);
         }
      }
      return null;
   }

   @Override
   public Token<Number> decimal() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         if ((masks[mark] & DECIMAL.mask) != 0) {
            return tokens.get(mark++);
         }
      }
      return null;
   }
   
   @Override
   public Line line(int position) {
      int length = tokens.size();
      int index = Math.min(length -1 , position);
      Token token = tokens.get(index);
      
      if(token != null) {
         return token.getLine();
      }
      return null;
   }

   @Override
   public int peek() {
      if (masks == null) {
         masks = indexer.index(tokens);
      }
      if (mark < masks.length) {
         return masks[mark];
      }
      return 0;
   }
   
   @Override
   public int reset(int position) {
      int current = mark;

      if (position <= count || position >= 0) {
         mark = position;
      }
      return current;
   }

   @Override
   public int count() {
      return tokens.size();
   }

   @Override
   public int mark() {
      return mark;
   }
}