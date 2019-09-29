package org.ternlang.core.stack;

import java.util.ArrayList;
import java.util.List;

public class OriginTraceFilter {
   
   public static final int DEFAULT_DEPTH = 0;
   public static final int DEBUG_DEPTH = 2; 

   private final OriginTraceType type;
   private final int depth;
   
   public OriginTraceFilter(OriginTraceType type) {
      this(type, DEFAULT_DEPTH);
   }
   
   public OriginTraceFilter(OriginTraceType type, int depth) {
      this.depth = depth;
      this.type = type;
   }
   
   public List<StackTraceElement> filter(Throwable cause, String... filters) {
      List<StackTraceElement> list = new ArrayList<StackTraceElement>();
   
      if(cause != null) {
         StackTraceElement[] elements = cause.getStackTrace();
         int count = Math.min(elements.length, depth);
         
         for(int i = 0; i < count; i++) {
            StackTraceElement element = elements[i];
            String source = element.getClassName();
            boolean match = false;
            
            for(String filter : filters) {
               match |= source.startsWith(filter);
            }
            if(match) {
               if(type.isInclude()) {
                  list.add(element);
               }
            } else {
               if(!type.isInclude()) {
                  list.add(element);
               }
               if(type.isFirst()) {
                  return list;
               }
            }
         } 
      }
      return list;
   }
}