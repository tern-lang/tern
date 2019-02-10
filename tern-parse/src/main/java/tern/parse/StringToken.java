package tern.parse;

public class StringToken implements Token<String>{
   
   private final String value;
   private final Line line;
   private final short type;
   
   public StringToken(String value) {
      this(value, null, 0);
   }
   
   public StringToken(String value, Line line, int type) {
      this.type = (short)type;
      this.value = value;
      this.line = line;
   }
   
   @Override
   public String getValue() {
      return value;
   }
   
   @Override
   public Line getLine() {
      return line;
   }
   
   @Override
   public short getType() {
      return type;
   }
   
   @Override
   public String toString() {
      return value;
   }
}