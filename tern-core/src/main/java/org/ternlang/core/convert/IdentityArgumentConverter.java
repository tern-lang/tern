package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.INVALID;

import org.ternlang.core.type.Type;
import org.ternlang.core.function.ArgumentConverter;

public class IdentityArgumentConverter implements ArgumentConverter {
   
   private final int count;
   
   public IdentityArgumentConverter(){
      this(0);
   }
   
   public IdentityArgumentConverter(int count){
      this.count = count;
   }
   
   @Override
   public Score score(Type... list) throws Exception {
      if(list.length == count) {
         return EXACT;
      }
      return INVALID;
   }

   @Override
   public Score score(Object... list) throws Exception {
      if(list.length == count) {
         return EXACT;
      }
      return INVALID;
   }
   
   @Override
   public Object[] assign(Object... list) throws Exception {
      return list;
   }

   @Override
   public Object[] convert(Object... list) throws Exception {
      return list;
   }
}