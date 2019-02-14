package org.ternlang.parse;

public interface TokenLexer {      
   Token<String> type();   
   Token<String> text();
   Token<String> template();
   Token<String> identifier();
   Token<String> qualifier();
   Token<String> literal(String text);
   Token<Number> hexidecimal();
   Token<Number> binary();
   Token<Number> decimal();
   Token<Character> space();
   Line line(int mark);
   int reset(int mark);
   int count();
   int peek();
   int mark();  
}