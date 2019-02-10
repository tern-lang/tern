package tern.core.convert;

public enum NumberType {
   DECIMAL,
   HEXIDECIMAL,
   CHARACTER,
   NONE;
   
   public boolean isCharacter() {
      return this == CHARACTER;
   }
   
   public boolean isDecimal() {
      return this == DECIMAL;
   }
   
   public boolean isHexidecimal() {
      return this == HEXIDECIMAL;
   }
}