package tern.core.function.index;

import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class FunctionKeyBuilder {

   private final TypeExtractor extractor;
   
   public FunctionKeyBuilder(TypeExtractor extractor) {
      this.extractor = extractor;
   }

   public Object create(String function, Object... list) throws Exception {
      if(list != null && list.length > 0) {
         Type[] types = new Type[list.length];
         
         for(int i = 0; i < list.length; i++) {
            Object value = list[i];
            
            if(value != null) {
               types[i] = extractor.getType(value);
            }
         }
         return new FunctionKey(function, types);
      }
      return function;
   }
   
   public Object create(String function, Type... types) throws Exception {
      if(types != null && types.length > 0) {
         return new FunctionKey(function, types);
      }
      return function;
   }
}