package org.ternlang.common.command;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

final class ConsoleWriter extends Writer {

   private final PrintWriter writer;
   public final OutputStream output;

   public ConsoleWriter(OutputStream output) {
      this.writer = new PrintWriter(output);
      this.output = output;
   }

   public void print(Object object) {
      writer.print(object);
   }

   public void print(String text) {
      writer.print(text);
   }

   public void println(String text) {
      writer.println(text);
   }

   public void printf(String text, Object... arguments) {
      writer.printf(text, arguments);
   }

   @Override
   public void write(char[] buffer, int offset, int length) {
      writer.write(buffer, offset, length);
   }

   @Override
   public void write(String text, int offset, int length) {
      writer.write(text, offset, length);
   }

   @Override
   public void flush() {
      writer.flush();
   }

   @Override
   public void close() {
      writer.close();
   }
}
