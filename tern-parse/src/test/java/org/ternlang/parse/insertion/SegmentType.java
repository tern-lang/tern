package org.ternlang.parse.insertion;

public enum SegmentType {
   DIRECTIVE, // shebang
   COMMENT, // comments
   SEMICOLON, // ;
   FLOATING,  // + - * %
   OPERATOR, // ++ --
   FLOW_CONTROL, // continue, break, return, yield, throw
   TYPE, // enum, class, trait
   BRANCH_CONDITION, // if, while, for
   BRANCH_NO_CONDITION,
   PREFIX, // loop, else
   SPACE, // space
   RETURN, // new line
   SYMBOL, // identifier
   OPEN_ARRAY,
   OPEN_EXPRESSION,
   OPEN_BLOCK,
   CLOSE_ARRAY,
   CLOSE_EXPRESSION,
   CLOSE_BLOCK,
   TEXT, // text
   NONE;

   public boolean isSymbol() {
      return this == SYMBOL;
   }

   public boolean isFlowControl() {
      return this == FLOW_CONTROL;
   }

   public boolean isReturn() {
      return this == RETURN;
   }

   public boolean isSpace() {
      return this == SPACE;
   }

   public boolean isFloating() {
      return this == FLOATING;
   }

   public boolean isOpenExpression() {
      return this == OPEN_EXPRESSION;
   }

   public boolean isCloseExpression() {
      return this == CLOSE_EXPRESSION;
   }

   public boolean isOpenArray() {
      return this == OPEN_ARRAY;
   }

   public boolean isCloseArray() {
      return this == CLOSE_ARRAY;
   }

   public boolean isOpenBlock() {
      return this == OPEN_BLOCK;
   }

   public boolean isCloseBlock() {
      return this == CLOSE_BLOCK;
   }

   public boolean isBranchCondition() {
      return this == BRANCH_CONDITION;
   }

   public boolean isBranchNoCondition() {
      return this == BRANCH_NO_CONDITION;
   }

   public boolean isType() {
      return this == TYPE;
   }
}

