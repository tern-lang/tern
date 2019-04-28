package org.ternlang.core.function.index;

import static org.ternlang.core.convert.Score.INVALID;

import java.util.List;

import org.ternlang.core.convert.Score;
import org.ternlang.core.function.ArgumentConverter;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Signature;
import org.ternlang.core.type.Type;

public class FunctionReducer {
   
   private final FunctionPointer invalid;
   
   public FunctionReducer() {
      this.invalid = new ErrorPointer();
   }

   public FunctionPointer reduce(List<FunctionPointer> pointers, String name, Type... types) throws Exception { 
      int size = pointers.size();
      
      if(size > 0) {
         FunctionPointer call = invalid;
         Score best = INVALID;
         
         for(int i = size - 1; i >= 0; i--) {
            FunctionPointer next = pointers.get(i);
            Function function = next.getFunction();
            String match = function.getName();
            
            if(match.equals(name)) {
               Signature signature = function.getSignature();
               ArgumentConverter converter = signature.getConverter();
               Score score = converter.score(types);

               if(score.compareTo(best) > 0) {
                  call = next;
                  best = score;
               }
            }
         }
         return call;
      }
      return invalid;
   }

   public FunctionPointer reduce(List<FunctionPointer> pointers, String name, Object... values) throws Exception { 
      int size = pointers.size();
      
      if(size > 0) {
         FunctionPointer call = invalid;
         Score best = INVALID;
         
         for(int i = size - 1; i >= 0; i--) {
            FunctionPointer next = pointers.get(i);
            Function function = next.getFunction();
            String match = function.getName();
            
            if(match.equals(name)) {
               Signature signature = function.getSignature();
               ArgumentConverter converter = signature.getConverter();
               Score score = converter.score(values);

               if(score.compareTo(best) > 0) {
                  call = next;
                  best = score;
               }
            }
         }
         return call;
      }
      return invalid;
   }
}