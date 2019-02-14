package org.ternlang.parse;

public class TextDecoder {
   
   private StringInterner interner;
   private char[] source;
   private int count;
   private int start;

   public TextDecoder(char[] source) {
      this(source, 0, source.length);
   }
   
   public TextDecoder(char[] source, int off, int count) {
      this.interner = new StringInterner();
      this.source = source;
      this.count = count;
      this.start = off;      
   }
   
   public String decode(int off, int length) {
      return decode(off, length, true);
   }
   
   public String decode(int off, int length, boolean escape) {
      int limit = start + length + off; 
      int read = start + off;
      int write = 0;

      if(length > 0 && escape) {
         char[] result = new char[length];
         
         while(read < limit) {
            char current = source[read];
            
            if(current == '\\' && read + 1 < limit) {
               char next = source[read + 1];
               
               if(next == 'u' || next == 'U') {         
                  result[write++] = escape(read + 2, 4);
                  read += 6;
               } else {
                  if(literal(next)){ // skip
                     result[write++] = source[read + 1];
                  } else if(next == 'r') {                  
                     result[write++] = '\r';
                  } else if(next == 'b') {                  
                     result[write++] = '\b';                     
                  } else if(next == 'n') {                  
                     result[write++] = '\n';
                  } else if(next == 't') {
                     result[write++] = '\t';
                  } else if(next == 'f') {
                     result[write++] = '\f';
                  } 
                  read += 2;
               }
            } else {
               result[write++] = source[read++];
            }
         }
         return interner.intern(result, 0, write);
      }
      return interner.intern(source, off, length);
   }
   
   private char escape(int off, int length) {
      int value = 0;
      
      for(int i = 0; i < length; i++) {
         char next = source[off + i];
         
         value <<= 4;
         value |= hexidecimal(next);
      }
      return (char)value;
   }
   
   private boolean literal(char value) {
      switch(value) {
      case '\\': case '\'':
      case '\"': case '`':
         return true;
      default:
         return false;
      }
   }
   
   public long binary(char value) {
      if(value == '0') {
         return 0; 
      }
      if(value == '1') {
         return 1; 
      }
      throw new IllegalArgumentException("Character '" + value + "' is not binary");
   }
   
   public long hexidecimal(char value) {
      if(value >= '0' && value <= '9') {
         return value - '0'; 
      }
      if(value >= 'a' && value <= 'f') {
         return 10 + (value - 'a'); 
      }
      if(value >= 'A' && value <= 'F') {
         return 10 + (value - 'A'); 
      }
      throw new IllegalArgumentException("Character '" + value + "' is not hexidecimal");
   }
}