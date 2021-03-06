package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.COMPATIBLE;
import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.ternlang.core.type.Type;

public class ByteConverter extends NumberConverter {

   private static final Class[] BYTE_TYPES = {
      Byte.class, 
      Short.class, 
      Integer.class, 
      AtomicInteger.class, 
      Long.class,
      AtomicLong.class, 
      BigInteger.class, 
      Double.class, 
      Float.class, 
      BigDecimal.class,
      Number.class
   };
   
   private static final Score[] BYTE_SCORES = {
      EXACT,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      SIMILAR,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE,
      COMPATIBLE
   };
   
   public ByteConverter(Type type) {
      super(type, BYTE_TYPES, BYTE_SCORES);
   }
}