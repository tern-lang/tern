package tern.parse;

public class SourceCode {
   
   private final char[] original;
   private final char[] source;
   private final short[] lines;
   private final short[] types;
   private final int count; // line count
   
   public SourceCode(char[] original, char[] source, short[] lines, short[] types, int count) {
      this.original = original;
      this.source = source;
      this.count = count;
      this.lines = lines;
      this.types = types;
   }
   
   public char[] getOriginal() {
      return original;
   }
   
   public char[] getSource() {
      return source;
   }
   
   public short[] getLines() {
      return lines;
   }
   
   public short[] getTypes() {
      return types;
   }
   
   public int getCount() {
      return count;
   }
   
   @Override
   public String toString() {
      return new String(source);
   }
}