package tern.parse;

import tern.common.ArrayStack;
import tern.common.Stack;

public class BraceStack {

   private final Stack<BraceNode> stack;
   
   public BraceStack() {
      this.stack = new ArrayStack<BraceNode>();
      stack.push(new BraceNode());
   }
   
   public boolean isEmpty() {
      return stack.peek().depth() == 0;
   }
   
   public void update(Token token) {
      Object value = token.getValue();
      
      if(value.equals("{")){
         System.err.println(">>>>>>>>>>>:   {" );
         stack.push(new BraceNode());
      }else if(value.equals("}")) {
         BraceNode node = stack.pop();
         int depth = node.depth();
         
         if(depth != 0) {
            throw new IllegalStateException("Bracket not closed");
         }
         System.err.println(">>>>>>>>>>>:   }" );
      }else if(value.equals("(")) {
         push(BraceType.NORMAL);
      }else if(value.equals(")")) {
         pop(BraceType.NORMAL);
      }else if(value.equals("[")) {
         push(BraceType.ARRAY);
      }else if(value.equals("]")) {
         pop(BraceType.ARRAY);
      }
   }
   
   public void push(BraceType type) {
      BraceNode node = stack.peek();
      
      if(node == null) {
         throw new IllegalStateException("No node");
      }
      System.err.println(">>>>>>>>>>>:   "+type.open);
      node.push(type);
   }
   
   public void pop(BraceType type) {
      BraceNode node = stack.peek();
      int depth = node.depth();
      
      if(depth == 0) {
         throw new IllegalStateException("Stack is empty");
      }
      System.err.println(">>>>>>>>>>>:   "+type.close);
      node.pop(type);
   }
   
   public static class BraceNode {
      
      private final Stack<BraceType> stack;
      
      public BraceNode() {
         this.stack = new ArrayStack<BraceType>();
      }
      
      public void push(BraceType type) {
         stack.push(type);
      }
      
      public void pop(BraceType type) {
         BraceType value = stack.pop();
         
         if(value != type) {
            throw new IllegalStateException("Unbalanced braces");
         }
      }
      
      public int depth() {
         return stack.size();
      }
   }
   
   public static enum BraceType {
      ARRAY("[", "]"),
      COMPOUND("{", "}"),
      NORMAL("(", ")");
      
      private final String open;
      private final String close;
      
      private BraceType(String open, String close) {
         this.open = open;
         this.close = close;
      }
   }
}
