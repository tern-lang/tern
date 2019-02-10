package tern.core.stack;

import java.util.Iterator;

import tern.core.function.Function;
import tern.core.trace.Trace;

public class StackElementIterator {
   
   private final Iterator iterator;
   
   public StackElementIterator(StackTrace stack) {
      this.iterator = stack.iterator();
   }
   
   public boolean hasNext() {
      return iterator.hasNext();
   }
   
   public StackElement next() {
      while(iterator.hasNext()) {
         Object value = iterator.next();
         
         if(Trace.class.isInstance(value)) {
            Trace trace = (Trace)value;
            
            while(iterator.hasNext()) {
               Object next = iterator.next();
               
               if(Function.class.isInstance(next)) {
                  return new StackElement(trace, (Function)next);
               }
            }
            return new StackElement(trace);
         }
      }
      return null;
   }
   

}