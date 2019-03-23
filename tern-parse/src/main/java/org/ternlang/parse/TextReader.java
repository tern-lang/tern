package org.ternlang.parse;

import static org.ternlang.parse.NumberCategory.DOUBLE;
import static org.ternlang.parse.NumberCategory.INTEGER;
import static org.ternlang.parse.TextCategory.BINARY;
import static org.ternlang.parse.TextCategory.DIGIT;
import static org.ternlang.parse.TextCategory.DOLLAR;
import static org.ternlang.parse.TextCategory.ESCAPE;
import static org.ternlang.parse.TextCategory.EXPONENT;
import static org.ternlang.parse.TextCategory.HEXADECIMAL;
import static org.ternlang.parse.TextCategory.IDENTIFIER;
import static org.ternlang.parse.TextCategory.LETTER;
import static org.ternlang.parse.TextCategory.PERIOD;
import static org.ternlang.parse.TextCategory.QUOTE;
import static org.ternlang.parse.TextCategory.SIGN;
import static org.ternlang.parse.TextCategory.SPACE;
import static org.ternlang.parse.TextCategory.SPECIAL;
import static org.ternlang.parse.TextCategory.SUFFIX;
import static org.ternlang.parse.TextCategory.TEMPLATE;

public class TextReader {

   private NumberCategoryMatcher matcher;
   private TextDecoder decoder;
   private short[] types;
   private char[] source;
   private int count;
   private int off;

   public TextReader(char[] source, short[] types) {
      this(source, types, 0, source.length);
   }
   
   public TextReader(char[] source, short[] types, int off, int count) {
      this.decoder = new TextDecoder(source, off, count);
      this.matcher = new NumberCategoryMatcher();
      this.source = source;
      this.count = count;
      this.types = types;
      this.off = off;      
   }       
   
   public char peek() {
      if(off < count) {
         return source[off];
      }
      return 0;
   }
   
   public char next() {
      if(off < count) {
         return source[off++];
      }
      return 0;
   }
   
   public boolean literal(char value) {
      if(off < count) {   
         char next = source[off];
         
         if(next == value) {
            off++;
            return true;
         }
      }
      return false;
   }
   
   public boolean literal(char[] text) {
      int length = text.length;      
      
      if(count - off >= length) {
         for(int i = 0; i < length; i++) {
            char value = text[i];
            char next = source[off + i];
            
            if(value != next) {
               return false;
            }
         }
         off += length;
         return true;
      }
      return false;
   }
   
   public boolean literal(String text) {
      int length = text.length();      
      
      if(count - off >= length) {
         for(int i = 0; i < length; i++) {
            char value = text.charAt(i);
            char next = source[off + i];
            
            if(value != next) {
               return false;
            }
         }
         off += length;
         return true;
      }
      return false;
   }
   
   public Character space() {
      if(off < count) {
         short type = types[off];
         
         if((type & SPACE) == SPACE) {           
            return source[off++];
         }
      }
      return null;
   }
   
   public Character letter() {
      if(off < count) {
         short type = types[off];
         
         if((type & LETTER) == LETTER) {           
            return source[off++];
         }
      }
      return null;
   }
   
   public Character digit() {      
      if(off < count) {
         short type = types[off];
         
         if((type & DIGIT) == DIGIT) {           
            return source[off++];
         }
      }
      return null;
   }
   
   public Number binary() {  
      if(off + 3 < count) {
         char first = source[off];
         char second = source[off+1];
 
         if(first != '0') { 
            return null;
         } 
         if(second != 'b' && second != 'B') {
            return null;
         }
         NumberCategory category = INTEGER;
         long value = 0;
         int pos = off + 2;
         int mark = off;
         
         while(pos < count) {
            short mask = types[pos];
            char next = source[pos];
            
            if((mask & BINARY) == BINARY) {
               value <<= 1;
               value |= decoder.binary(next);
            } else {
               if((mask & SUFFIX) == SUFFIX) {
                  category = matcher.match(next);
                  off++;
               } 
               break;
            }
            pos++;
         }
         if(pos > mark + 2) {                  
            off = pos;
            return category.convert(value);
         }
      }
      return null;      
   } 
   
   public Number hexadecimal() {
      if(off + 3 < count) {
         char first = source[off];
         char second = source[off+1];
         
         if(first != '0') { 
            return null;
         } 
         if(second != 'x' && second != 'X') {
            return null;
         }
         NumberCategory category = INTEGER;
         long value = 0;
         int pos = off + 2;
         int mark = off;
         
         while(pos < count) {
            short mask = types[pos];
            char next = source[pos];
            
            if((mask & HEXADECIMAL) == HEXADECIMAL) {
               value <<= 4;
               value |= decoder.hexadecimal(next);
            } else {
               if((mask & SUFFIX) == SUFFIX) {
                  category = matcher.match(next);
                  off++;
               } 
               break;
            }
            pos++;
         }
         if(pos > mark + 2) {                  
            off = pos;
            return category.convert(value);
         }
      }
      return null;      
   }    
   
   public Number decimal() {
      NumberCategory category = INTEGER;
      double scale = 0;
      long number = 0;
      int exponent = 0;
      int mark = off;
      int sign = 0; // exponent sign

      while (off < count) {
         char next = source[off];
         short mask = types[off]; 

         if ((mask & DIGIT) == 0) {
            if (off > mark) {
               if((mask & PERIOD) == PERIOD && scale == 0) {
                  if (off + 1 < count) {
                     mask = types[off + 1];

                     if ((mask & DIGIT) == DIGIT) {
                        category = DOUBLE;
                        scale = 1.0d;
                        off++;
                        continue;
                     }
                  }
               } else if((mask & EXPONENT) == EXPONENT && sign == 0) { // 12.32e-10
                  if (off + 1 < count) {
                     mask = types[off + 1];
                     next = source[off + 1];
                     category = DOUBLE;
                     sign = 1;
                     off++;

                     if ((mask & SIGN) == SIGN) {
                        sign = next == '-' ? -1 : 1;
                        off++;
                     }
                     continue;
                  }
               } else if((mask & SUFFIX) == SUFFIX) {
                  category = matcher.match(next);
                  off++; 
               }               
               break;
            }
         } else {
            if(sign == 0) {
               number *= 10;
               number += next;
               number -= '0';
               scale *= 10.0d;
            } else {
               exponent *= 10;
               exponent += next;
               exponent -= '0';
            }
         }
         off++;
      }
      if (off > mark) {
         double result = number;
         double factor = 1.0d;

         if(scale > 0) {
            result /= scale;
         }
         if(exponent > 0) {
            factor = Math.pow(10, sign * exponent);
         }
         return category.convert(result * factor);
      }
      return null;
   }   
   
   public String text() {
      int mark = off + 1;
      int pos = off + 1;
      
      if(pos < count) {
         char start = source[off];
         short mask = types[off];
         char next = start;
         
         if((mask & QUOTE) == QUOTE) {
            int escape = 0;
            int length = 0;
            
            while(pos < count) {
               next = source[pos++];
               
               if(next == start) {
                  off = pos;
                  break;
               }
               mask = types[pos -1];
               
               if((mask & ESCAPE) == ESCAPE){
                  if(pos + 1 < count) {                     
                     mask = types[pos];
                     
                     if((mask & SPECIAL) == SPECIAL) {
                        escape++;
                        length++;
                        pos++;
                     }
                  }
               }
               length++;
            }
            if(next == start && off > mark){
               return decoder.decode(mark, length, escape > 0); 
            }
         }
      }
      return null;
   }
   
   public String template() {
      int mark = off + 1;
      int pos = off + 1;
      
      if(pos < count) {
         char start = source[off];
         short mask = types[off];
         char next = start;
         
         if((mask & TEMPLATE) == TEMPLATE) {
            int escape = 0;
            int length = 0;
            int variable = 0;
            
            while(pos < count) {
               next = source[pos++];
               
               if(next == start) {
                  if(variable > 0) {
                     off = pos;
                  }
                  break;
               }
               mask = types[pos -1];
               
               if((mask & DOLLAR) == DOLLAR){
                  variable++;
               } else if((mask & ESCAPE) == ESCAPE){
                  if(pos + 1 < count) {                     
                     mask = types[pos];
                     
                     if((mask & SPECIAL) == SPECIAL) {
                        escape++;
                        length++;
                        pos++;
                     }
                  }
               }
               length++;
            }
            if(next == start && off > mark && variable > 0){
               return decoder.decode(mark, length, escape > 0); 
            }
         }
      }
      return null;
   }
   
   public String type() {
      int mark = off;
      
      if(off < count) {
         short type = types[off];
         
         if((type & LETTER) == LETTER) {
            char start = source[off];
            char capital = decoder.capital(start);

            if(capital != start) {
               return null;
            }
            int length = 0;

            while(off < count) {
               type = types[off];
               
               if((type & (IDENTIFIER | DOLLAR)) == 0) {
                  break;
               }
               length++;
               off++;
            }       
            if(length > 0) {
               return decoder.decode(mark, length, false); 
            }
         }
      }
      return null;             
   }   
   
   public String identifier() {
      int mark = off;
      
      if(off < count) {
         short type = types[off];
         
         if((type & LETTER) == LETTER) {
            int length = 0;
            
            while(off < count) {
               type = types[off];
               
               if((type & IDENTIFIER) == 0) {
                  break;
               }
               length++;
               off++;
            }       
            if(length > 0) {
               return decoder.decode(mark, length, false); 
            }
         }
      }
      return null;             
   }
   
   public String qualifier() {  
      int mark = off;
      
      if(off < count) {
         short start = types[off];
         
         if((start & LETTER) == LETTER) {
            int length = 0;         
      
            while(off < count) {
               short next = types[off];
               
               if((next & (PERIOD | DOLLAR)) != 0) {
                  if(off + 1 < count) {
                     next = types[off + 1];
                     
                     if((next & LETTER) == 0) {
                        break; 
                     }
                  }                  
               } else if((next & IDENTIFIER) == 0) {
                  break;
               }
               length++;
               off++;
            }       
            if(length > 0) {
               return decoder.decode(mark, length, false);         
            }
         }
      }
      return null;
          
   }   
   
   public int reset(int mark) {
      int current = off;
      
      if(mark > count || mark < 0){
         throw new ParseException("Illegal reset for position " + mark);
      }
      off = mark; 
      return current;
   }  
   
   public int count() {
      return count;
   }
   
   public int mark() {
      return off;
   }    
}