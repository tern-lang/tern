package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.INVALID;
import static org.ternlang.core.convert.Score.POSSIBLE;
import static org.ternlang.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.ternlang.core.error.InternalArgumentException;
import org.ternlang.core.type.Type;

public class NumberConverter extends ConstraintConverter {
   
   private static final Class[] NUMBER_TYPES = {
      Number.class,
      Integer.class, 
      Long.class, 
      Double.class, 
      Float.class, 
      Short.class, 
      Byte.class,
      BigInteger.class, 
      AtomicInteger.class, 
      AtomicLong.class,
      BigDecimal.class
   };
   
   private static final Score[] NUMBER_SCORES = {
      EXACT,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR
   };

   protected final AliasResolver resolver;
   protected final ScoreMapper mapper;
   protected final Type type;
   
   public NumberConverter(Type type) {
      this(type, NUMBER_TYPES, NUMBER_SCORES);
   }
   
   public NumberConverter(Type type, Class[] types, Score[] scores) {
      this.mapper = new ScoreMapper(types, scores);
      this.resolver = new AliasResolver();
      this.type = type;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Type type = resolver.resolve(actual);
         Class real = type.getType();
         
         if(real != null) {
            Class promote = promoter.promote(real);
            Score score = mapper.map(promote);
            
            if(score != null) {
               return score;
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
         Class actual = value.getClass();
         Class parent = actual.getSuperclass();
         
         if(parent == Number.class) {
            return convert(require, (Number)value);
         }
         throw new InternalArgumentException("Conversion from " + actual + " to " + require + " is not possible");
      }
      if(require.isPrimitive()) {
         throw new InternalArgumentException("Invalid conversion from null to primitive number");
      }
      return null;
   }
}