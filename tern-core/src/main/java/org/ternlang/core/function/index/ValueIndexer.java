package org.ternlang.core.function.index;

import org.ternlang.core.convert.Score;
import org.ternlang.core.function.ArgumentConverter;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Signature;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.variable.Value;

public class ValueIndexer {

   private final ClosureAdapter adapter;
   private final ThreadStack stack;

   public ValueIndexer(ClosureAdapter adapter, ThreadStack stack) {
      this.adapter = adapter;
      this.stack = stack;
   }

   public FunctionPointer index(Value value, Object... values) throws Exception { // match function variable
      Object object = value.getValue();

      if (Function.class.isInstance(object)) {
         Function function = (Function) object;
         Signature signature = function.getSignature();
         ArgumentConverter match = signature.getConverter();
         Score score = match.score(values);

         if (score.isValid()) {
            return new TracePointer(stack, function);
         }
      }
      return adapter.adapt(object);
   }
}
