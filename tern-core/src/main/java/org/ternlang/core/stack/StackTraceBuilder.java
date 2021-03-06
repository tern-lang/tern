package org.ternlang.core.stack;

import static org.ternlang.core.Reserved.IMPORT_JAVA;
import static org.ternlang.core.Reserved.IMPORT_JAVA_REFLECT;
import static org.ternlang.core.Reserved.IMPORT_JDK_INTERNAL;
import static org.ternlang.core.Reserved.IMPORT_SUN_INTERNAL;
import static org.ternlang.core.Reserved.IMPORT_SUN_PROXY;
import static org.ternlang.core.Reserved.IMPORT_TERN;
import static org.ternlang.core.stack.StackTraceType.INCLUDE_FIRST;

import java.util.List;

public class StackTraceBuilder {
   
   private static final String[] EXCLUDE = {
      IMPORT_TERN, 
      IMPORT_JAVA_REFLECT, 
      IMPORT_JDK_INTERNAL, 
      IMPORT_SUN_INTERNAL,
      IMPORT_SUN_PROXY
   };
   
   private static final String[] INCLUDE = {
      IMPORT_TERN, 
      IMPORT_JAVA 
   };

   private final StackElementConverter builder;
   private final StackTraceFilter filter;
   private final StackTraceElement[] empty;
   
   public StackTraceBuilder() {
      this.filter = new StackTraceFilter(INCLUDE_FIRST);
      this.builder = new StackElementConverter();
      this.empty = new StackTraceElement[]{};
   }
   
   public StackTraceElement[] create(StackTrace stack) {
      return create(stack, null);
   }
   
   public StackTraceElement[] create(StackTrace stack, Throwable origin) {
      Thread thread = Thread.currentThread();
      List<StackTraceElement> list = filter.filter(origin, INCLUDE); // debug cause
      List<StackTraceElement> context = builder.create(stack); // script stack
      StackTraceElement[] actual = thread.getStackTrace(); // native stack
      
      for(StackTraceElement trace : context) {
         list.add(trace);
      }
      for(int i = 1; i < actual.length; i++) { // strip Thread.getStackTrace
         StackTraceElement trace = actual[i];
         String source = trace.getClassName();
         boolean exclude = false;
         
         for(String filter : EXCLUDE) {
            exclude |= source.startsWith(filter); // not really correct, stripping required elements!
         }
         if(!exclude) {
            list.add(trace);
         }
      } 
      return list.toArray(empty);
   }
}