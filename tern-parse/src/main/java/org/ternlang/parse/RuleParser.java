package org.ternlang.parse;

import java.util.ArrayList;
import java.util.List;

public class RuleParser extends StringParser implements RuleIterator {
   
   private List<Rule> rules;
   private String syntax;
   private String name;
   private int index;

   public RuleParser(String name, String syntax) {
      this.rules = new ArrayList<Rule>();
      this.syntax = syntax;
      this.name = name;
   }
   
   @Override
   public boolean hasNext() {
      if(rules.isEmpty()) {
         parse(syntax);
      } 
      return index < rules.size(); 
   }
   
   @Override
   public Rule peek() {
      if(hasNext()) {
         return rules.get(index);         
      }
      return null;
   }
   
   @Override
   public Rule next() {
      Rule symbol = peek();
      
      if(symbol != null) {
         index++;
      }
      return symbol;
   }

   @Override
   protected void init() {
      rules.clear();
      index = 0;
      off = 0;     
   }

   @Override
   protected void parse() {
      pack();
      digest();
   }   
   
   private void pack() {
      if(off < count) {
         char last = source[off];
         int size = count;
         int write = 0;
         int read = 0;

         while(read < size){
            char next = source[read++];
            
            if(next == '\'' && last != '\\'){ 
               source[write++] = next;
               
               while(read < size){
                  last = source[read-1];
                  next = source[read++];  
                  source[write++] = next;
                  
                  if(next =='\'' && last!='\\'){  
                     break;
                  }
               }
            }else if(!space(next)){            
               last = source[read - 1];  
               source[write++] = last;                   
            }         
         }
         count = write;
      }
   }
   
   private void digest() {
      while(off < count) {
         if(skip("|")){
            digest(RuleType.SPLITTER);
         } else if(skip("*")){
            digest(RuleType.REPEAT);
         } else if(skip("+")){
            digest(RuleType.REPEAT_ONCE);            
         } else if(skip("?")){
            digest(RuleType.OPTIONAL);               
         } else if(skip("(")) {
            digest(RuleType.OPEN_GROUP);
         } else if(skip(")")) {
            digest(RuleType.CLOSE_GROUP);
         } else if(skip("{")) {
            digest(RuleType.OPEN_CHOICE);
         } else if(skip("}")) {
            digest(RuleType.CLOSE_CHOICE);            
         } else if(skip("[")) {
            digest(RuleType.SPECIAL);             
         } else if(skip("<")) {
            digest(RuleType.REFERENCE);            
         } else if(skip("'")) {
            digest(RuleType.LITERAL);
         } else if(skip("_")) {
            digest(RuleType.SPACE);
         } else {
            throw new ParseException("Invalid syntax in " + name + " at " + off);
         }
      } 
   }
   
   private void digest(RuleType type) {
      int mark = off - 1;
      
      if(type.terminal != null) {         
         while(off < count) {
            if(skip(type.terminal)) {
               break;            
            }            
            off++;         
         }         
         digest(type, mark + 1, (off - mark) - 2);
      } else {
         digest(type, mark, off - mark);
      }
   }

   private void digest(RuleType type, int off, int length){
      if(length <= 0) {
         throw new ParseException("Invalid rule of type " + type + " in " + name + " at " + off);
      }
      String text = new String(source, off, length);
      Rule token = new Rule(type, text, name);
      
      rules.add(token);
   }
}