package tern.core.function.index;

import tern.core.function.Function;
import tern.core.stack.ThreadStack;

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