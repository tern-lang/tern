package org.ternlang.core.type.extend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.ternlang.common.Consumer;

public class ReaderExtension {

   public ReaderExtension() {
      super();
   }
   
   public Reader buffer(Reader reader, int size) throws IOException {
      return new BufferedReader(reader, size);
   }
   
   public String readText(Reader reader) throws IOException {
      StringBuilder builder = new StringBuilder();
      char[] data = new char[1024];
      int count = 0;
      
      try {
         while((count = reader.read(data)) != -1) {
            builder.append(data, 0, count);
         }
         return builder.toString();
      } finally {
         reader.close();
      }
   }
   
   public List<String> readLines(Reader reader) throws IOException {
      LineNumberReader iterator = new LineNumberReader(reader);
      List<String> lines = new ArrayList<String>();
      
      try {
         while(true) {
            String line = iterator.readLine();
            
            if(line == null) {
               return lines;
            }
            lines.add(line);
         }
      } finally {
         iterator.close();
      }
   }

   public void forEachLine(Reader reader, Consumer<String, ?> consumer) throws IOException {
      LineNumberReader iterator = new LineNumberReader(reader);

      try {
         while(true) {
            String line = iterator.readLine();

            if(line == null) {
               break;
            }
            consumer.consume(line);
         }
      } finally {
         iterator.close();
      }
   }
   
   public void copyTo(Reader reader, Writer writer) throws IOException {
      char[] data = new char[1024];
      int count = 0;
      
      try {
         while((count = reader.read(data)) != -1) {
            writer.write(data, 0, count);
         }
      } finally {
         reader.close();
      }
   }

   public void copyTo(Reader reader, StringBuilder builder) throws IOException {
      char[] data = new char[1024];
      int count = 0;
      
      try {
         while((count = reader.read(data)) != -1) {
            builder.append(data, 0, count);
         }
      } finally {
         reader.close();
      }
   }
}
