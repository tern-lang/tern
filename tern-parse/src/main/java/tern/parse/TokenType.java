package tern.parse;

public enum TokenType {
   IDENTIFIER(0, 0x0001),
   TYPE(1, 0x0002),   
   QUALIFIER(2, 0x0004),   
   HEXIDECIMAL(3, 0x0008),   
   BINARY(4, 0x0010),  
   DECIMAL(5, 0x0020),
   TEXT(6, 0x0040),
   LITERAL(7, 0x0080),
   TEMPLATE(8, 0x0100),
   SPACE(9, 0x0200);
   
   public final short mask;
   public final int index;
   
   private TokenType(int index, int mask) {
      this.mask = (short)mask;
      this.index = index;
   }
   
   public int getIndex(){
      return index;
   }
   
   public int getMask(){
      return mask;
   }
}