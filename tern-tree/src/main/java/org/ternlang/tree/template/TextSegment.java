package org.ternlang.tree.template;

import java.io.Writer;

import org.ternlang.core.scope.Scope;

public class TextSegment implements Segment {
   
   private final String text;
   private final char[] source;
   private final int off;
   private final int length;
   
   public TextSegment(char[] source, int off, int length) {
      this.text = new String(source, off, length);
      this.source = source;
      this.length = length;
      this.off = off;         
   }
   
   @Override
   public void process(Scope scope, Writer writer) throws Exception {
      writer.write(source, off, length);
   } 
   
   @Override
   public String toString() {
      return text;
   }
}