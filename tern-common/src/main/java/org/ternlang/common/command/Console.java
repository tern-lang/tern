package org.ternlang.common.command;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Iterator;

public class Console implements Iterable<String> {

   private final LineNumberReader parser;
   private final Reader reader;

   public Console(InputStream source) {
      this.reader = new InputStreamReader(source);
      this.parser = new LineNumberReader(reader);
   }

   @Override
   public Iterator<String> iterator() {
      return new LineIterator(parser);
   }
   
   private static class LineIterator implements Iterator<String> {
      
      private LineNumberReader reader;
      private String line;
      
      public LineIterator(LineNumberReader reader) {
         this.reader = reader;
      }

      @Override
      public boolean hasNext() {
         try {
            if(line == null) {
               line = reader.readLine();
            }
         } catch(Exception e) {
            return false;
         }
         return line != null;
      }

      @Override
      public String next() {
         try {
            if(hasNext()) {
               return line;
            }
         } finally {
            line = null;
         }
         return line;
      }
   }

}