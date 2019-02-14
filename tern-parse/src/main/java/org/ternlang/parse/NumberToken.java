package org.ternlang.parse;

public class NumberToken implements Token<Number> {
   
   private final Number value;
   private final Line line;
   private final short type;

   public NumberToken(Number value) {
      this(value, null, 0);
   }
   
   public NumberToken(Number value, Line line, int type) {
      this.type = (short)type;
      this.value = value;
      this.line = line;
   }
   
   @Override
   public Number getValue() {
      return value;
   }
   
   @Override
   public Line getLine(){ 
      return line;
   }
   
   @Override
   public short getType() {
      return type;
   }
   
   @Override
   public String toString(){
      return String.valueOf(value);
   }
}