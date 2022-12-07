package org.ternlang.core.stack;

import org.ternlang.core.platform.PlatformType;

public class ThreadLocalStack extends ThreadLocal<StackTrace> {
   
   private static final int LARGE_STACK = 1000;
   private static final int SMALL_STACK = 40;
   
   private final PlatformType platform;
   
   public ThreadLocalStack() {
      this.platform = PlatformType.resolveType();
   }

   @Override
   public StackTrace initialValue() {      
      if(platform.isStandard()) {
         return new StackTrace(LARGE_STACK);
      }
      return new StackTrace(SMALL_STACK);
   }
}