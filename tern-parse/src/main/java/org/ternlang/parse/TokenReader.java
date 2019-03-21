package org.ternlang.parse;

public interface TokenReader {
   boolean literal(String value);   
   boolean decimal();
   boolean binary();
   boolean hexadecimal();
   boolean identifier();
   boolean qualifier();
   boolean template();
   boolean space();
   boolean text();
   boolean type(); 
}