package tern.core.stack;

import static tern.core.Reserved.IMPORT_TERN;

import java.util.List;

public class StackTraceBuilder {
   
   private final OriginTraceExtractor extractor;
   private final StackElementConverter builder;
   private final StackTraceElement[] empty;
   
   public StackTraceBuilder() {
      this.extractor = new OriginTraceExtractor();
      this.builder = new StackElementConverter();
      this.empty = new StackTraceElement[]{};
   }
   
   public StackTraceElement[] create(StackTrace stack) {
      return create(stack, null);
   }
   
   public StackTraceElement[] create(StackTrace stack, Throwable origin) {
      Thread thread = Thread.currentThread();
      List<StackTraceElement> list = extractor.extract(origin); // debug cause
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