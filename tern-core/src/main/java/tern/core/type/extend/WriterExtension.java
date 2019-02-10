package tern.core.type.extend;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class WriterExtension {

   public WriterExtension() {
      super();
   }
   
   public Writer buffer(Writer writer, int size) throws IOException {
      return new BufferedWriter(writer, size);
   }
}
