package org.ternlang.core.stack;

import static org.ternlang.core.Reserved.IMPORT_JAVA;
import static org.ternlang.core.Reserved.IMPORT_TERN;
import static org.ternlang.core.stack.OriginTraceType.INCLUDE_FIRST;

import java.util.List;

public class StackTraceBuilder {

   private final StackElementConverter builder;
   private final OriginTraceFilter filter;
   private final StackTraceElement[] empty;
   
   public StackTraceBuilder() {
      this.filter = new OriginTraceFilter(INCLUDE_FIRST);
      this.builder = new StackElementConverter();
      this.empty = new StackTraceElement[]{};
   }
   
   public StackTraceElement[] create(StackTrace stack) {
      return create(stack, null);
   }
   
   public StackTraceElement[] create(StackTrace stack, Throwable origin) {
      Thread thread = Thread.currentThread();
      List<StackTraceElement> list = filter.filter(origin, IMPORT_TERN, IMPORT_JAVA); // debug cause
      List<StackTraceElement> context = builder.create(stack); // script stack
      StackTraceElement[] actual = thread.getStackTrace(); // native stack
      
      for(StackTraceElement trace : context) {
         list.add(trace);
      }
      for(int i = 1; i < actual.length; i++) { // strip Thread.getStackTrace
         StackTraceElement trace = actual[i];
         String source = trace.getClassName();
         
         if(!source.startsWith(IMPORT_TERN)) { // not really correct, stripping required elements!
            list.add(trace);
         }
      } 
      return list.toArray(empty);
   }
}