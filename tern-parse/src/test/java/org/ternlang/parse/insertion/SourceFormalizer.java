package org.ternlang.parse.insertion;

import java.util.Iterator;

public class SourceFormalizer {

   private final SegmentProcessor processor;
   private final StringBuilder builder;
  // private final BraceStack stack;

   public SourceFormalizer(char[] source) {
      this.processor = new SegmentProcessor(source);
      this.builder = new StringBuilder();
      //this.stack = new BraceStack();
   }

//   public char[] formalize() {
//      Iterator<Segment> segments = processor.process();
//
//      while(segments.hasNext()) {
//
//      }
//   }

   private static class Block {
      // remember the last couple of tokens in the block
   }
}
