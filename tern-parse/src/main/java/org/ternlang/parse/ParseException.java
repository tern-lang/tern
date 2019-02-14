package org.ternlang.parse;

public class ParseException extends RuntimeException {
   
   public ParseException(String message) {
      super(message);
   }
   
   public ParseException(String message, Throwable cause) {
      super(message, cause);
   }
}