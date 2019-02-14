package org.ternlang.core.type.extend;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class OutputStreamExtension {

   public OutputStreamExtension() {
      super();
   }
   
   public OutputStream buffer(OutputStream source, int buffer) throws IOException {
      return new BufferedOutputStream(source);
   }
   
   public Writer writer(OutputStream source) throws IOException {
      return new OutputStreamWriter(source);
   }
   
   public Writer writer(OutputStream source, String charset) throws IOException {
      return new OutputStreamWriter(source, charset);
   }
}
