package tern.core.function.index;

import static tern.core.convert.Score.INVALID;

import java.util.List;

import tern.core.convert.Score;
import tern.core.function.ArgumentConverter;
import tern.core.function.Function;
import tern.core.function.Signature;
import tern.core.stack.ThreadStack;
import tern.core.type.Type;

public class FunctionReducer {
   
   private final FunctionPointer invalid;
   
   public FunctionReducer(ThreadStack stack) {
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