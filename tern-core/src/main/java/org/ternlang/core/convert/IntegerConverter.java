package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.COMPATIBLE;
import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.ternlang.core.type.Type;

public class IntegerConverter extends NumberConverter {
   
   private static final Class[] INTEGER_TYPES = {
      Integer.class, 
      Long.class, 
      BigInteger.class, 
      AtomicInteger.class, 
      AtomicLong.class,
      Double.class, 
      Float.class, 
      BigDecimal.class, 
      Short.class, 
      Byte.class,
      Number.class
   };
   
   private static final Score[] INTEGER_SCORES = {
      EXACT,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE
   };

   public IntegerConverter(Type type) {
      super(type, INTEGER_TYPES, INTEGER_SCORES);
   }
   
   @Override
   public Object convert(Object value) throws Exception {
      if(value != null) {
         Class actual = value.getClass();

         if(actual == Character.class) {
            Character code = (Character)value;
            int number = code.charValue();
            
            return number;
         }
      }
      return super.convert(value);
   }
}