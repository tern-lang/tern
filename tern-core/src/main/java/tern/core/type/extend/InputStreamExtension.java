package tern.core.type.extend;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class InputStreamExtension {
   
   public InputStreamExtension() {
      super();
   }

   public InputStream buffer(InputStream source, int size) throws IOException {
      return new BufferedInputStream(source, size);
   }

   public Reader reader(InputStream source) throws IOException {
      return new InputStreamReader(source);
   }
   
   public Reader reader(InputStream source, String charset) throws IOException {
      return new InputStreamReader(source, charset);
   }

   public byte[] readBytes(InputStream source) throws IOException {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] data = new byte[1024];
      int count = 0;
      
      try {
         while((count = source.read(data)) != -1) {
            buffer.write(data, 0, count);
         }
         return buffer.toByteArray();
      } finally {
         source.close();
      }
   }
   
   public void copyTo(InputStream source, OutputStream output) throws IOException {
      byte[] data = new byte[1024];
      int count = 0;
      
      try {
         while((count = source.read(data)) != -1) {
            output.write(data, 0, count);
         }
      } finally {
         source.close();
      }
   }
}
