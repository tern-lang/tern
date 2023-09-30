package org.ternlang.common.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

final class ConsoleReader extends Reader {

   private final LineNumberReader reader;
   private final LineIterator lines;
   private final Reader decoder;

   public ConsoleReader(InputStream input) {
      this.decoder = new InputStreamReader(input);
      this.reader = new LineNumberReader(decoder);
      this.lines = new LineIterator(reader);
   }

   public Stream<String> readLines() throws IOException {
      return StreamSupport.stream(
           Spliterators.spliteratorUnknownSize(
                lines,
                Spliterator.ORDERED), false);
   }

   public String readLine() throws IOException {
      return reader.readLine();
   }

   @Override
   public int read(char[] buffer, int offset, int length) throws IOException {
      return reader.read(buffer, offset, length);
   }

   @Override
   public int read(CharBuffer buffer) throws IOException {
      return reader.read(buffer);
   }

   @Override
   public int read() throws IOException {
      return reader.read();
   }

   @Override
   public long skip(long count) throws IOException {
      return reader.skip(count);
   }

   @Override
   public boolean ready() throws IOException {
      return reader.ready();
   }

   @Override
   public boolean markSupported() {
      return reader.markSupported();
   }

   @Override
   public void mark(int limit) throws IOException {
      reader.mark(limit);
   }

   @Override
   public void reset() throws IOException {
      reader.reset();
   }

   @Override
   public void close() throws IOException {
      reader.close();
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

      @Override
      public void remove() {
         throw new UnsupportedOperationException("Illegal modification");
      }
   }
}