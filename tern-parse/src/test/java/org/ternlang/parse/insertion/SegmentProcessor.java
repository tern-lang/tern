package org.ternlang.parse.insertion;

import org.ternlang.parse.SourceException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.ternlang.parse.insertion.SegmentType.BRANCH_CONDITION;
import static org.ternlang.parse.insertion.SegmentType.BRANCH_NO_CONDITION;
import static org.ternlang.parse.insertion.SegmentType.CLOSE_ARRAY;
import static org.ternlang.parse.insertion.SegmentType.CLOSE_BLOCK;
import static org.ternlang.parse.insertion.SegmentType.CLOSE_EXPRESSION;
import static org.ternlang.parse.insertion.SegmentType.COMMENT;
import static org.ternlang.parse.insertion.SegmentType.DIRECTIVE;
import static org.ternlang.parse.insertion.SegmentType.FLOATING;
import static org.ternlang.parse.insertion.SegmentType.FLOW_CONTROL;
import static org.ternlang.parse.insertion.SegmentType.OPEN_ARRAY;
import static org.ternlang.parse.insertion.SegmentType.OPEN_BLOCK;
import static org.ternlang.parse.insertion.SegmentType.OPEN_EXPRESSION;
import static org.ternlang.parse.insertion.SegmentType.OPERATOR;
import static org.ternlang.parse.insertion.SegmentType.RETURN;
import static org.ternlang.parse.insertion.SegmentType.SEMICOLON;
import static org.ternlang.parse.insertion.SegmentType.SPACE;
import static org.ternlang.parse.insertion.SegmentType.SYMBOL;
import static org.ternlang.parse.insertion.SegmentType.TEXT;
import static org.ternlang.parse.insertion.SegmentType.TYPE;

public class SegmentProcessor {

   private static final String[] TYPES = {"class", "trait", "enum"};
   private static final String[] FLOW_CONTROLS = {"return", "throw", "new", "assert", "await", "break", "continue", "yield", "debug"};
   private static final String[] BRANCH_CONDITIONS = {"if", "for", "while", "until", "catch"};
   private static final String[] BRANCH_NO_CONDITIONS = {"else", "loop", "try"};

   private SegmentList segments;
   private char[] original;
   private int count;
   private int line;
   private int off;

   public SegmentProcessor(char[] original) {
      this.segments = new SegmentList(original);
      this.count = original.length;
      this.original = original;
      this.line = 1;
   }

   public Iterator<Segment> process() {
      if (off < count) {
         directive(); // read interpreter directive
      }
      while (off < count) {
         char next = original[off];

         if (comment(next)) {
            if(!comment() && !operator()) {
               throw new SourceException("Invalid comment at line " + line);
            }
         } else if (quote(next)) {
            if(!string()) {
               throw new SourceException("Invalid string at line " + line);
            }
         } else if (space(next)) {
            if(!space()) {
               throw new SourceException("Invalid space at line " + line);
            }
         } else if (identifier(next)) {
            if(!symbol()) {
               throw new SourceException("Invalid symbol at line " + line);
            }
         } else if (open(next) || close(next)) {
            if(!brace()) {
               throw new SourceException("Invalid brace at line " + line);
            }
         } else {
            if (!operator()) {
               throw new SourceException("Invalid source at line " + line);
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
                     line++;
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
                  line++;
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

                  if(terminal == '\n') {
                     line++;
                  }
                  if (prev == '*') {
                     off++;
                     return segments.add(COMMENT, mark, off);
                  }
               }
               off++;
            }
            throw new SourceException("Comment not closed at line " + line);
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
         if(next == '\n') {
            line++;
         }
         off++;
         size++;
      }
      throw new SourceException("String literal not closed at line " + line);
   }

   private boolean space() {
      int mark = off;
      int lines = 0;

      while (off < count) {
         char next = original[off];

         if (!space(next)) {
            return segments.add(lines > 0 ? RETURN : SPACE, mark, off);
         }
         if(next == '\n') {
            lines++;
            line++;
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
            Range range = new Range(original, mark, off);

            for (String value : FLOW_CONTROLS) {
               if (value.contentEquals(range)) {
                  return segments.add(FLOW_CONTROL, value);
               }
            }
            for (String value : TYPES) {
               if (value.contentEquals(range)) {
                  return segments.add(TYPE, value);
               }
            }
            for (String value : BRANCH_CONDITIONS) {
               if (value.contentEquals(range)) {
                  return segments.add(BRANCH_CONDITION, value);
               }
            }
            for (String value : BRANCH_NO_CONDITIONS) {
               if (value.contentEquals(range)) {
                  return segments.add(BRANCH_NO_CONDITION, value);
               }
            }
            return segments.add(SYMBOL, range);
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
            if(mark + 1 == off) {
               switch(next) {
                  case ';':
                     return segments.add(SEMICOLON, mark, off);
                  case '-': case '+':
                  case '%': case '/':
                  case '*': case '.':
                  case '?': case ':':
                  case '&': case '|':
                  case '>': case '<':
                  case '^': case '=':
                     return segments.add(FLOATING, mark, off);
               }
            }
            return segments.add(OPERATOR, mark, off);
         }
      }
      return segments.add(OPERATOR, mark, off);
   }

   private boolean brace() {
      char start = original[off];

      if (open(start)) {
         switch(start) {
            case '{':
               return segments.add(OPEN_BLOCK, off++, off);
            case '(' :
               return segments.add(OPEN_EXPRESSION, off++, off);
            case '[' :
               return segments.add(OPEN_ARRAY, off++, off);
         }
         throw new SourceException("Invalid brace " + start + " at line " + line);
      }
      if (close(start)) {
         switch(start) {
            case '}':
               return segments.add(CLOSE_BLOCK, off++, off);
            case ')' :
               return segments.add(CLOSE_EXPRESSION, off++, off);
            case ']' :
               return segments.add(CLOSE_ARRAY, off++, off);
         }
         throw new SourceException("Invalid brace " + start + " at line " + line);
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

      public boolean add(SegmentType type, CharSequence text) {
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

         private final CharSequence token;
         private final SegmentType type;

         public LiteralSegment(SegmentType type, CharSequence token) {
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
            return token.toString();
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
      }
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
