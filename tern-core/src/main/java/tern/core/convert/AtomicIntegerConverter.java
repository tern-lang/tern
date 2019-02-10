package tern.core.convert;

import static tern.core.convert.Score.COMPATIBLE;
import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.SIMILAR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import tern.core.type.Type;

public class AtomicIntegerConverter extends NumberConverter {

   private static final Class[] ATOMIC_INTEGER_TYPES = {
      AtomicInteger.class, 
      Integer.class,
      Short.class, 
      BigInteger.class, 
      Long.class,
      AtomicLong.class, 
      Double.class, 
      Float.class, 
      BigDecimal.class, 
      Byte.class,
      Number.class
   };
   
   private static final Score[] ATOMIC_INTEGER_SCORES = {
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
   
   public AtomicIntegerConverter(Type type) {
      super(type, ATOMIC_INTEGER_TYPES, ATOMIC_INTEGER_SCORES);
   }
}