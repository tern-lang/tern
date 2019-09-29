package org.ternlang.core.stack;

public enum OriginTraceType {
   INCLUDE_FIRST,
   INCLUDE_ALL,
   EXCLUDE_FIRST,
   EXCLUDE_ALL;
   
   public boolean isInclude() {
      return this == INCLUDE_FIRST || this == INCLUDE_ALL;
   }
   
   public boolean isFirst() {
      return this == INCLUDE_FIRST || this == EXCLUDE_FIRST;
   }
}
