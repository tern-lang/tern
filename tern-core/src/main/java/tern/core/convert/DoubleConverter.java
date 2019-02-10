package tern.core.convert;

import static tern.core.convert.Score.COMPATIBLE;
import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import tern.core.type.Type;

public class DoubleConverter extends NumberConverter {
   
   private static final Class[] DOUBLE_TYPES = {
      Double.class, 
      Float.class, 
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
   
   private static final Score[] DOUBLE_SCORES = {
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
   
   public DoubleConverter(Type type) {
      super(type, DOUBLE_TYPES, DOUBLE_SCORES);
   }
}