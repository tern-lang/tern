package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.INVALID;
import static org.ternlang.core.convert.Score.POSSIBLE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.ternlang.core.error.InternalArgumentException;
import org.ternlang.core.type.Type;

public class CharacterConverter extends ConstraintConverter {

   private static final Class[] CHARACTER_TYPES = {
      Character.class,
      Double.class, 
      Float.class, 
      BigDecimal.class, 
      Long.class, 
      AtomicLong.class,
      Integer.class, 
      BigInteger.class, 
      AtomicInteger.class, 
      Short.class, 
      Byte.class
   };
   
   private static final Score[] CHARACTER_SCORES = {
      EXACT,
      POSSIBLE,
      POSSIBLE,
      POSSIBLE,
      POSSIBLE,
      POSSIBLE,
      POSSIBLE,
      POSSIBLE,
      POSSIBLE,
      POSSIBLE,
      POSSIBLE
   };   
   
   private final CharacterMatcher matcher;
   private final ScoreMapper mapper;
   private final Type type;
   
   public CharacterConverter(Type type) {
      this.mapper = new ScoreMapper(CHARACTER_TYPES, CHARACTER_SCORES);
      this.matcher = new CharacterMatcher();
      this.type = type;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         
         if(real != null) {
            Class promote = promoter.promote(real);
            Score score = mapper.map(promote);
            
            if(score != null) {
               return score;
            }
            if(real == String.class) {
               return POSSIBLE;
            }
         }
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      Class require = type.getType();
      
      if(value != null) {
         Class actual = value.getClass();
         Score score = mapper.map(actual);
         
         if(score == null) {
            if(actual == String.class) {
               String text = String.valueOf(value);
               
               if(matcher.matchCharacter(text)) {
                  return POSSIBLE;
               }
            }
            return INVALID;
         }
         return score;
      }
      if(require.isPrimitive()) {
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Object convert(Object value) throws Exception {
      Class require = type.getType();
      
      if(value != null) {
         Class type = value.getClass();
         
         if(type == Character.class) {
            return value;
         }
         if(type == String.class) {
            return convert(require, (String)value);
         }
         Class parent = type.getSuperclass();
         
         if(parent == Number.class) {
            return convert(require, (Number)value);
         }
         throw new InternalArgumentException("Conversion from " + type + " to character is not possible");
      }
      if(require.isPrimitive()) {
         throw new InternalArgumentException("Invalid conversion from null to primitive character");
      }
      return null;
   }
}