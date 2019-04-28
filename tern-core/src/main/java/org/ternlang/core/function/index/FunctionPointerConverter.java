package org.ternlang.core.function.index;

import org.ternlang.core.function.Function;

public class FunctionPointerConverter {
   
   public FunctionPointerConverter() {
      super();
   }
   
   public FunctionPointer convert(Function function) {
      return new TracePointer(function);
   }

   public Function convert(FunctionPointer pointer) {
      return pointer.getFunction();
   }
}