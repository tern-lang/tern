package tern.core.function.index;

import tern.core.convert.Score;
import tern.core.function.ArgumentConverter;
import tern.core.function.Function;
import tern.core.function.Signature;
import tern.core.stack.ThreadStack;
import tern.core.variable.Value;

public class ValueIndexer {
   
   private final ThreadStack stack;
   
   public ValueIndexer(ThreadStack stack) {
      this.stack = stack;
   }
   
   public FunctionPointer index(Value value, Object... values) throws Exception { // match function variable
      Object object = value.getValue();
      
      if(Function.class.isInstance(object)) {
         Function function = (Function)object;
         Signature signature = function.getSignature();
         ArgumentConverter match = signature.getConverter();
         Score score = match.score(values);
         
         if(score.isValid()) {
            return new TracePointer(function, stack); 
         }
      }
      return null;
   }
}