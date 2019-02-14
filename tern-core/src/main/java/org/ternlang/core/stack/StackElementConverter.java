package org.ternlang.core.stack;

import java.util.LinkedList;
import java.util.List;

public class StackElementConverter {
   
   public StackElementConverter() {
      super();
   }
   
   public List<StackTraceElement> create(StackTrace stack) {
      List<StackTraceElement> list = new LinkedList<StackTraceElement>();
      StackElementIterator iterator = new StackElementIterator(stack);
      
      while(iterator.hasNext()) {
         StackElement next = iterator.next();
         
         if(next != null) {
            StackTraceElement trace = next.build();
         
            if(trace != null) {
               list.add(trace);
            }
         }
      }
      return list;
   }
}