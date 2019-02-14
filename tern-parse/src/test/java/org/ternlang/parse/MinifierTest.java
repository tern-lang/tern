package org.ternlang.parse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import junit.framework.TestCase;

public class MinifierTest extends TestCase {
   
   public void testMinifier1() throws Exception{
      System.err.println(minify("/test_source1.tern"));
      System.err.println(minify("/test_source2.tern"));
   }
   
   public String minify(String resource) throws Exception {
      InputStream input = MinifierTest.class.getResourceAsStream(resource);
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] chunk = new byte[1024];
      int count = 0;
      
      while((count=input.read(chunk))!=-1){
         buffer.write(chunk, 0, count);
      }
      String source = buffer.toString("UTF-8");
      char[] text = source.toCharArray();
      SourceCompressor compressor = new SourceCompressor(text);
      SourceCode code = compressor.compress();
      char[] minified = code.getSource();
      return new String(minified);
   }

}
