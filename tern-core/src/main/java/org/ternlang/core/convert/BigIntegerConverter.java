package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.COMPATIBLE;
import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.ternlang.core.type.Type;

public class BigIntegerConverter extends NumberConverter {

   private static final Class[] BIG_INTEGER_TYPES = {
      BigInteger.class, 
      Integer.class, 
      AtomicInteger.class, 
      Short.class, 
      Long.class,
      AtomicLong.class, 
      Double.class, 
      Float.class, 
      BigDecimal.class, 
      Byte.class,
      Number.class
   };
   
   private static final Score[] BIG_INTEGER_SCORES = {
      EXACT,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE
   };
   
   public BigIntegerConverter(Type type) {
      super(type, BIG_INTEGER_TYPES, BIG_INTEGER_SCORES);
   }
}