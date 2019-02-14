package org.ternlang.parse;

public enum RuleType {
   SPLITTER("|"),
   REPEAT("*"),
   REPEAT_ONCE("+"),
   OPTIONAL("?"), 
   OPEN_GROUP("("),
   CLOSE_GROUP(")"),
   OPEN_CHOICE("{"),
   CLOSE_CHOICE("}"),   
   SPACE("_"), 
   SPECIAL("[", "]"),   
   REFERENCE("<", ">"),
   LITERAL("'", "'");
   
   public final String terminal;
   public final String start;

   private RuleType(String start) {
      this(start, null);
   }
   
   private RuleType(String start, String terminal) {
      this.terminal = terminal;
      this.start = start;
   }
   
   public boolean isToken() {
      return this == SPECIAL || this == REFERENCE || this == LITERAL || this == SPACE;
   }
   
   public boolean isOptional() {
      return this == OPTIONAL;
   }
   
   public boolean isSplitter() {
      return this == SPLITTER;
   }
   
   public boolean isRepeat() {
      return this == REPEAT;
   }
   
   public boolean isRepeatOnce() {
      return this == REPEAT_ONCE;
   }
   
   public boolean isOpenChoice(){
      return this == OPEN_CHOICE;
   }    
   
   public boolean isCloseChoice(){
      return this == CLOSE_CHOICE;
   }   
   
   public boolean isOpenGroup(){
      return this == OPEN_GROUP;
   }

   public boolean isCloseGroup(){
      return this == CLOSE_GROUP;
   }
   
   public boolean isSpecial() {
      return this == SPECIAL;
   }    
   
   public boolean isReference() {
      return this == REFERENCE;
   } 
   
   public boolean isLiteral() {
      return this == LITERAL;
   }
   
   public boolean isSpace() {
      return this == SPACE;
   }
}