package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.COMPATIBLE;
import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.ternlang.core.type.Type;

public class FloatConverter extends NumberConverter {
   
   private static final Class[] FLOAT_TYPES = {
      Float.class, 
      Double.class, 
      BigDecimal.class, 
      Long.class, 
      AtomicLong.class,
      Integer.class, 
      BigInteger.class, 
      AtomicInteger.class, 
      Short.class, 
      Byte.class,
      Number.class
   };
   
   private static final Score[] FLOAT_SCORES = {
      EXACT,
      SIMILAR,
      SIMILAR,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE
   };
   
   public FloatConverter(Type type) {
      super(type, FLOAT_TYPES, FLOAT_SCORES);
   }
}