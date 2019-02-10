package tern.parse;

public class ArrayQueue {

   private Object[] queue;
   private int length;
   private int start;
   private int head; // add at head
   private int tail; // remove at tail
   private int size;
   private int end;
   
   public ArrayQueue(Object[] queue, int start, int length) {
      this.end = start + length;
      this.length = length;
      this.start = start;
      this.queue = queue;
      this.head = start;
      this.tail = start;
   }
   
   public void offer(Object value) {
      if(head >= end) {
         head = start; // wrap around
      }
      if(size < length) {
         size++; // if we have not reached capacity
      } else {
         if(tail == head) { // writing over tail
            if(tail + 1 == end) { // already at end
               tail = start; // wrap to start
            } else {
               tail++; // next index
            }
         }        
      }
      queue[head++] = value;
   }
   
   public Object poll() {
      if(size > 0) {
         Object value = queue[tail++];
         
         if(tail >= end) {
            tail = start;
         }
         size--;
         return value;
      }
      return null;
   }
   
   public int size() {
      return size;
   }
}
