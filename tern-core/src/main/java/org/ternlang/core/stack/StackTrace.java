package org.ternlang.core.stack;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.ternlang.common.ArrayStack;
import org.ternlang.common.Stack;
import org.ternlang.core.function.Function;
import org.ternlang.core.trace.Trace;

public class StackTrace implements Iterable  {
   
   private final Stack<Function> functions;
   private final Stack<Object> stack;
   private final AtomicInteger depth;
   private final int threshold;

   public StackTrace() {
      this(40);
   }
   
   public StackTrace(int threshold) {
      this.functions = new ArrayStack<Function>();
      this.stack = new ArrayStack<Object>();
      this.depth = new AtomicInteger();
      this.threshold = threshold;
   }
   
   public Function current() {
      return functions.peek();
   }
   
   @Override
   public Iterator iterator(){
      return stack.iterator();
   }
   
   public void before(Trace trace) {
      stack.push(trace);
   }
   
   public void before(Function function) {
      int size = depth.incrementAndGet();
      
      functions.push(function);
      stack.push(function);
      
      if(size > threshold) {
         throw new StackOverflowException("Stack overflow for " + function);   
      }     
   }
   
   public void after(Trace trace) { // remove from stack
      while(!stack.isEmpty()) {
         Object next = stack.pop();
         
         if(next == trace) {
            break;
         }
         Function context = functions.peek();
         
         if(next == context) {
            functions.pop();
            depth.getAndDecrement();
         } 
      }
   }
   
   public void after(Function function) { 
      while(!stack.isEmpty()) {
         Object next = stack.pop();

         if(next == function) {
            functions.pop();
            depth.getAndDecrement();
            break;
         }
         Function context = functions.peek();
         
         if(next == context) {
            functions.pop();
            depth.getAndDecrement();
         }
      }
   }
   
   public void clear() {
      stack.clear();
   }
}