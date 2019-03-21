package org.ternlang.core.convert;

public enum NumberType {
   DECIMAL,
   HEXADECIMAL,
   CHARACTER,
   NONE;
   
   public boolean isCharacter() {
      return this == CHARACTER;
   }
   
   public boolean isDecimal() {
      return this == DECIMAL;
   }
   
   public boolean isHexadecimal() {
      return this == HEXADECIMAL;
   }
}