package org.ternlang.parse.insertion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.ternlang.parse.insertion.SegmentType.COMMENT;
import static org.ternlang.parse.insertion.SegmentType.DIRECTIVE;
import static org.ternlang.parse.insertion.SegmentType.OPERATOR;
import static org.ternlang.parse.insertion.SegmentType.RETURN;
import static org.ternlang.parse.insertion.SegmentType.*;
import static org.ternlang.parse.insertion.SegmentType.SYMBOL;
import static org.ternlang.parse.insertion.SegmentType.TEXT;

public class SegmentProcessor {

   private SegmentList segments;
   private char[] original;
   private int count;
   private int off;

   public SegmentProcessor(char[] original) {
      this.segments = new SegmentList(original);
      this.count = original.length;
      this.original = original;
   }

   public Iterator<Segment> process() {
      if (off < count) {
         directive(); // read interpreter directive
      }
      while (off < count) {
         char next = original[off];

         if (comment(next)) {
            if(!comment() && !operator()) {
               throw new IllegalStateException("Invalid comment");
            }
         } else if (quote(next)) {
            if(!string()) {
               throw new IllegalStateException("Invalid string");
            }
         } else if (space(next)) {
            if(!space()) {
               throw new IllegalStateException("Invalid space");
            }
         } else if (identifier(next)) {
            if(!symbol()) {
               throw new IllegalStateException("Invalid symbol");
            }
         } else if (open(next) || close(next)) {
            if(!brace()) {
               throw new IllegalStateException("Invalid open brace");
            }
         } else {
            if (!operator()) {
               throw new IllegalStateException("Illegal source (" + next + ") " + new String(original, off, count - off));
            }
         }
      }
      return segments.iterator();
   }

   private boolean directive() {
      char start = original[off];
      int mark = off;

      if (directive(start)) {
         if (off + 1 < count) {
            char next = original[off + 1];

            if (next == '!') {
               while (off < count) {
                  char terminal = original[off];

                  if (terminal == '\n') {
                     off++;
                     return segments.add(DIRECTIVE, mark, off);
                  }
                  off++;
               }
               return segments.add(DIRECTIVE, mark, off); // end of source
            }
         }
      }
      return false;
   }

   private boolean comment() {
      int mark = off;

      if (off + 1 < count) {
         char next = original[off + 1];

         if (next == '/') {
            while (off < count) {
               char terminal = original[off];

               if (terminal == '\n') {
                  off++;
                  return segments.add(COMMENT, mark, off);
               }
               off++;
            }
            return segments.add(COMMENT, mark, off); // end of source
         }
         if (next == '*') {
            while (off < count) {
               char terminal = original[off];

               if (terminal == '/' && off > 0) {
                  char prev = original[off - 1];

                  if (prev == '*') {
                     off++;
                     return segments.add(COMMENT, mark, off);
                  }
               }
               off++;
            }
            return segments.add(COMMENT, mark, off);
         }
      }
      return false;
   }

   private boolean string() {
      char start = original[off];
      int mark = off;
      int size = 0;

      while (off < count) {
         char next = original[off];

         if (next == start) {
            if (size == 1) { // "" or ''
               off++;
               return segments.add(TEXT, mark, off);
            }
            if (off > 0 && size > 0) {
               char prev = original[off - 1];

               if (!escape(prev)) {
                  off++;
                  return segments.add(TEXT, mark, off);
               }
               for (int i = 1; i <= size; i++) {
                  char value = original[off - i];

                  if (!escape(value)) {
                     if (i % 2 == 1) {
                        off++;
                        return segments.add(TEXT, mark, off);
                     }
                     break;
                  }
               }
            }
         }
         off++;
         size++;
      }
      return segments.add(TEXT, mark, off);
   }

   private boolean space() {
      int mark = off;
      int lines = 0;

      while (off < count) {
         char next = original[off];

         if ((!space(next))) {
            return segments.add(lines > 0 ? RETURN : SPACE, mark, off);
         }
         off++;
      }
      return segments.add(lines > 0 ? RETURN : SPACE, mark, off);
   }

   private boolean symbol() {
      int mark = off;

      while (off < count) {
         char next = original[off];

         if (!identifier(next)) {
            return segments.add(SYMBOL, mark, off);
         }
         off++;
      }
      return segments.add(SYMBOL, mark, off);
   }

   private boolean operator() {
      int mark = off;

      while (off < count) {
         char next = original[off++];

         if (!space(next) || !identifier(next) || !comment(next) || !quote(next)) {
            return segments.add(OPERATOR, mark, off);
         }
      }
      return segments.add(OPERATOR, mark, off);
   }

   private boolean brace() {
      char start = original[off];

      if (open(start)) {
         return segments.add(OPEN, off++, off);
      }
      if (close(start)) {
         return segments.add(CLOSE, off++, off);
      }
      return false;
   }

   private boolean escape(char value) {
      return value == '\\';
   }

   private boolean directive(char value) {
      return value == '#';
   }

   private boolean comment(char value) {
      return value == '/';
   }

   private boolean quote(char value) {
      switch (value) {
         case '"':
         case '\'':
         case '`':
            return true;
         default:
            return false;
      }
   }

   private boolean space(char value) {
      switch (value) {
         case ' ':
         case '\t':
         case '\n':
         case '\r':
            return true;
         default:
            return false;
      }
   }

   private boolean identifier(char value) {
      if (value >= 'a' && value <= 'z') {
         return true;
      }
      if (value >= 'A' && value <= 'Z') {
         return true;
      }
      if (value >= '0' && value <= '9') {
         return true;
      }
      return value == '_';
   }

   private boolean open(char value) {
      return value == '{' || value == '(' || value == '[';
   }

   private boolean close(char value) {
      return value == '}' || value == ')' || value == ']';
   }

   private static class SegmentList implements Iterable<Segment> {

      private final List<Segment> segments;
      private final char[] source;

      public SegmentList(char[] source) {
         this.segments = new ArrayList<>();
         this.source = source;
      }

      @Override
      public Iterator<Segment> iterator() {
         return segments.iterator();
      }

      public boolean add(SegmentType type, String text) {
         Segment segment = new LiteralSegment(type, text);
         return segments.add(segment);
      }

      public boolean add(SegmentType type, int from, int to) {
         Segment segment = new RangeSegment(type, source, from, to);
         return segments.add(segment);
      }

      @Override
      public String toString() {
         return segments.toString();
      }

      private static class LiteralSegment implements Segment {

         private final SegmentType type;
         private final String token;

         public LiteralSegment(SegmentType type, String token) {
            this.token = token;
            this.type = type;
         }

         @Override
         public CharSequence source() {
            return token;
         }

         @Override
         public SegmentType type() {
            return type;
         }

         @Override
         public String toString() {
            return token;
         }
      }

      private static class RangeSegment implements Segment {

         private final SegmentType type;
         private final Range range;

         public RangeSegment(SegmentType type, char[] source, int from, int to) {
            this.range = new Range(source, from, to);
            this.type = type;
         }

         @Override
         public CharSequence source() {
            return range;
         }

         @Override
         public SegmentType type() {
            return type;
         }

         @Override
         public String toString() {
            return range.toString();
         }

         private static class Range implements CharSequence {

            private final char[] source;
            private final int length;
            private final int off;

            public Range(char[] source, int from, int to) {
               this.length = to - from;
               this.source = source;
               this.off = from;
            }

            @Override
            public int length() {
               return length;
            }

            @Override
            public char charAt(int index) {
               return source[off + index];
            }

            @Override
            public CharSequence subSequence(int start, int end) {
               return new Range(source, off + start, off + end);
            }

            @Override
            public String toString() {
               return new String(source, off, length);
            }
         }
      }


   }
}
