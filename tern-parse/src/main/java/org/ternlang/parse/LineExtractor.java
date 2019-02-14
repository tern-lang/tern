package org.ternlang.parse;

public class LineExtractor {

   private final String resource;
   private final Line[] lines;
   private final char[] source;
   
   public LineExtractor(String resource, char[] source, int lines) {
      this.lines = new Line[lines + 1];
      this.resource = resource;
      this.source = source;
   }
   
   public Line extract(int line) {
      Line result = lines[line];
      
      if(result == null) {
         result = lines[line] = new Reference(line);
      }
      return result;
   }
   
   private String create(int line) {
      short start = 0;
      short length = 0;
      short count = 1;
      
      for(int i = 0; i < source.length; i++) {
         char next = source[i];
         
         if(count < line) {
            start++;
         } else {
            length++;
         }
         if(next == '\n') {
            if(count + 1 > line) {
               return new String(source, start, length - 1);
            }
            count++;
         }
      }
      return new String(source, start, source.length - start);
   }
   
   private class Reference implements Line {
      
      private final int line;
      
      public Reference(int line) {
         this.line = line;
      }
      
      @Override
      public String getSource() {
         return create(line);
      }
      
      @Override
      public String getResource() {
         return resource;
      }
      
      @Override
      public int getNumber() {
         return line;
      }
      
      @Override
      public String toString() {
         return String.valueOf(line);
      }
   }
}