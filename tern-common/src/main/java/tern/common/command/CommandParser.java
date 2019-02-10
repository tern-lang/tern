package tern.common.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CommandParser implements Iterable<String> {

   private List<String> tokens;
   private StringBuilder token;
   private char[] source;
   private int count;
   private int off;

   public CommandParser(String command) {
      this.tokens = new ArrayList<String>();
      this.token = new StringBuilder();
      this.source = command.toCharArray();
      this.count = source.length;
   }

   public List<String> command() {
      if (tokens.isEmpty()) {
         parse();
      }
      return Collections.unmodifiableList(tokens);
   }

   @Override
   public Iterator<String> iterator() {
      return command().iterator();
   }

   private void parse() {
      seek();
      tokens();
   }

   private void tokens() {
      while (off < count) {
         char next = source[off];

         if (quote(next)) {
            literal();
         } else {
            value();
         }
         insert();
         seek();
      }
   }

   private void insert() {
      String value = token.toString();

      if (!value.isEmpty()) {
         tokens.add(value);
      }
      reset();
   }

   private void reset() {
      token.setLength(0);
   }

   private void seek() {
      while (off < count) {
         if (!space(source[off])) {
            break;
         }
         off++;
      }
   }

   private void value() {
      while (off < count) {
         char next = source[off++];

         if (space(next)) {
            break;
         }
         token.append(next);
      }
   }

   private void literal() {
      char open = source[off];
      int start = off + 1;
      int length = 0;

      if (quote(open)) {
         off++;

         while (off < count) {
            if (open == source[off++]) {
               break;
            }
         }
         length = (off - start) - 1;
      }
      if (length > 0) {
         token.append(source, start, length);
      }
   }

   private boolean quote(char value) {
      switch (value) {
      case '\'':
      case '\"':
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
}