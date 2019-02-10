package tern.core.convert;

import static tern.core.convert.Score.INVALID;
import static tern.core.convert.Score.SIMILAR;

import tern.core.type.Type;
import tern.core.function.ArgumentConverter;

public class VariableArgumentConverter implements ArgumentConverter { 
   
   private final ConstraintConverter[] converters;

   public VariableArgumentConverter(ConstraintConverter[] converters) {
      this.converters = converters;
   }
   
   @Override
   public Score score(Type... list) throws Exception {
      if(list.length > 0) {
         int require = converters.length;
         int start = require - 1;
         int remaining = list.length - start;
         
         if(remaining < 0) {
            return INVALID;
         }
         Score total = INVALID;
         
         for(int i = 0; i < start; i++){
            ConstraintConverter converter = converters[i];
            Type type = list[i];
            Score score = converter.score(type);
            
            if(score.isInvalid()) {
               return INVALID;
            }
            total = Score.sum(total, score);
         }
         if (remaining > 0) {
            for (int i = 0; i < remaining; i++) {
               ConstraintConverter converter = converters[require - 1];
               Type value = list[i + start];
               Score score = converter.score(value);
               
               if(score.isInvalid()) {
                  return INVALID;
               }
               total = Score.sum(total, score);
            }
         }
         return total;
      }
      if(converters.length == 1) {
         return SIMILAR;
      }
      return INVALID;
   }
   
   @Override
   public Score score(Object... list) throws Exception {
      if(list.length > 0) {
         int require = converters.length;
         int start = require - 1;
         int remaining = list.length - start;
         
         if(remaining < 0) {
            return INVALID;
         }
         Score total = INVALID;
         
         for(int i = 0; i < start; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            Score score = converter.score(value);
            
            if(score.isInvalid()) {
               return INVALID;
            }
            total = Score.sum(total, score);
         }
         if (remaining > 0) {
            for (int i = 0; i < remaining; i++) {
               ConstraintConverter converter = converters[require - 1];
               Object value = list[i + start];
               Score score = converter.score(value);
               
               if(score.isInvalid()) {
                  return INVALID;
               }
               total = Score.sum(total, score);
            }
         }
         return total;
      }
      if(converters.length == 1) {
         return SIMILAR;
      }
      return INVALID;
   }
   
   @Override
   public Object[] assign(Object... list) throws Exception {
      if(list.length > 0) {
         int require = converters.length;
         int start = require - 1;
         int remaining = list.length - start;
         
         for(int i = 0; i < start; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            
            list[i] = converter.assign(value);
         }
         if (remaining > 0) {
            for (int i = 0; i < remaining; i++) {
               ConstraintConverter converter = converters[require - 1];
               Object value = list[i + start];
               
               list[i + start] = converter.assign(value);
            }
         }
      }
      return list;
   }

   @Override
   public Object[] convert(Object... list) throws Exception {
      if(list.length > 0) {
         int require = converters.length;
         int start = require - 1;
         int remaining = list.length - start;
         
         for(int i = 0; i < start; i++){
            ConstraintConverter converter = converters[i];
            Object value = list[i];
            
            list[i] = converter.convert(value);
         }
         if (remaining > 0) {
            for (int i = 0; i < remaining; i++) {
               ConstraintConverter converter = converters[require - 1];
               Object value = list[i + start];
               
               list[i + start] = converter.convert(value);
            }
         }
      }
      return list;
   }
}