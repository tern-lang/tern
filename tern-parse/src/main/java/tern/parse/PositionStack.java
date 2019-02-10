package tern.parse;

public class PositionStack {
   
   private static final int VISIT_COUNT = 10000;

   private int[] visit;
   private int[] stack;
   private int top;

   public PositionStack() {
      this(1024);
   }
   
   public PositionStack(int capacity) {
      this.visit = new int[VISIT_COUNT];
      this.stack = new int[capacity];
   }      
   
   public int depth(int offset, int grammar) {
      int count = visit[grammar];
      
      if(count != 0) {
         int key = offset << 10 | grammar; // use grammar and offset
         
         for(int i = top - 1; i >= 0; i--) {
            int next = stack[i];
            
            if(next == key) {
               return top - (i + 1);
            }
         }
      }
      return -1;  
   }     

   public void push(int offset, int grammar) {
      int capacity = stack.length;
      
      if(top >= capacity) {
         int[] copy = new int[capacity * 2];
         
         if(top > 0) {
            System.arraycopy(stack, 0, copy, 0, stack.length);
         }
         stack = copy;
      }
      int key = offset << 10 | grammar;
      
      stack[top++] = key;
      visit[grammar]++;

   }

   public int pop(int offset, int grammar) {
      int count = visit[grammar];
      
      if(count != 0) {
         int key = offset << 10 | grammar;
         
         while(top > 0) {
            int value = stack[top-- -1];
            
            if(value == key){
               visit[grammar]--;
               return value;
            }
         }
      }
      return -1;
   }
   
   public void clear() {
      for(int i = 0; i < visit.length; i++) {
         visit[i] = 0;
      }
      top =0;
   }
   
   public int size() {
      return top;
   }
}