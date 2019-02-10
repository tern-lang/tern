package tern.core.function;

import java.lang.reflect.Array;
import java.util.List;

import tern.core.error.InternalStateException;

public class SignatureAligner {

   private final Signature signature;
   
   public SignatureAligner(Signature signature) {
      this.signature = signature;
   }
   
   public Object[] align(Object... list) throws Exception {
      if(signature.isVariable()) {
         List<Parameter> parameters = signature.getParameters();
         int require = parameters.size();
         int actual = list.length;
         int start = require - 1;
         int remaining = actual - start;
         
         if(remaining >= 0) {
            Object array = new Object[remaining];
            
            for(int i = 0; i < remaining; i++) {
               try {
                  Array.set(array, i, list[i + start]);
               } catch(Exception e){
                  throw new InternalStateException("Invalid argument at " + i + " for" + signature, e);
               }
            }
            Object[] copy = new Object[require];
            
            if(require > list.length) {
               System.arraycopy(list, 0, copy, 0, list.length);
            } else {
               System.arraycopy(list, 0, copy, 0, require);
            }
            copy[start] = array;
            return copy;
         }
      }
      return list;
   }
}