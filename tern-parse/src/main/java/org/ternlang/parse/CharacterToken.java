package org.ternlang.parse;

public class CharacterToken implements Token<Character>{
   
   private final Character value;
   private final Line line;
   private final short type;
   
   public CharacterToken(Character value) {
      this(value, null, 0);
   }
   
   public CharacterToken(Character value, Line line, int type) {
      this.type = (short)type;
      this.value = value;
      this.line = line;
   }
   
   @Override
   public Character getValue() {
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
   public String toString(){
      return String.valueOf(value);
   }   
}