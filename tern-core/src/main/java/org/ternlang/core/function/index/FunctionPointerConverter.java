package org.ternlang.core.function.index;

import org.ternlang.core.function.Function;
import org.ternlang.core.stack.ThreadStack;

public class FunctionPointerConverter {
   
   private final ThreadStack stack;
   
   public FunctionPointerConverter(ThreadStack stack) {
      this.stack = stack;
   }
   
   public FunctionPointer convert(Function function) {
      return new TracePointer(function, stack);
   }

   public Function convert(FunctionPointer pointer) {
      return pointer.getFunction();
   }
}