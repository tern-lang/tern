package tern.core.convert;

import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.INVALID;

import tern.core.type.Type;
import tern.core.function.ArgumentConverter;

public class FixedArgumentConverter implements ArgumentConverter { 

   private final ConstraintConverter[] converters;

   public FixedArgumentConverter(ConstraintConverter[] converters) {
      this.converters = converters;
   }
   
   @Override
   public Score score(Type... list) throws Exception {
      if(list.length != converters.length) {
         return INVALID;
      }
      if(list.length > 0) {
         Score total = INVALID; 
      
         for(int i = 0; i < list.length; i++){
            ConstraintConverter converter = converters[i];
            Type type = list[i];
            Score score = converter.score(type);
            
            if(score.isInvalid()) {
               return INVALID;
            }
            total = Score.sum(total, score);
            
         }
         return total;
      }
      return EXACT;
   }
   
   @Override
   public Score score(Object... list) throws Exception {
      if(list.length != converters.length) {
         return INVALID;
      }
      if(list.length > 0) {
         Score total = INVALID; 
      
         for(int i = 0; i < list.length; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            Score score = converter.score(value);
            
            if(score.isInvalid()) {
               return INVALID;
            }
            total = Score.sum(total, score);
            
         }
         return total;
      }
      return EXACT;
   }
   
   @Override
   public Object[] assign(Object... list) throws Exception {
      if(list.length > 0) {
         for(int i = 0; i < list.length; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            
            list[i] = converter.assign(value);
         }
         return list;
      }
      return list;
   }
   
   @Override
   public Object[] convert(Object... list) throws Exception {
      if(list.length > 0) {
         for(int i = 0; i < list.length; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            
            list[i] = converter.convert(value);
         }
         return list;
      }
      return list;
   }
}