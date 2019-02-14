package org.ternlang.core.convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.ternlang.core.error.InternalArgumentException;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.type.Type;

public abstract class ConstraintConverter  {
   
   protected final PrimitivePromoter promoter;
   protected final ConstraintAdapter adapter;
   
   protected ConstraintConverter() {
      this.promoter = new PrimitivePromoter();
      this.adapter = new ConstraintAdapter();
   }
   
   protected Object convert(Class type, String text) throws Exception {
      Class actual = promoter.promote(type);
      
      try {
         if(actual == String.class) {
            return text;
         }
         if(actual == Integer.class) {
            return adapter.createInteger(text);
         }
         if(actual == Double.class) {
            return adapter.createDouble(text);
         }
         if(actual == Float.class) {
            return adapter.createFloat(text);
         }
         if(actual == Boolean.class) {
            return adapter.createBoolean(text);
         }
         if(actual == Byte.class) {
            return adapter.createByte(text);
         }
         if(actual == Short.class) {
            return adapter.createShort(text);
         }
         if(actual == Long.class) {
            return adapter.createLong(text);
         }
         if(actual == AtomicLong.class) {
            return adapter.createAtomicLong(text);
         }
         if(actual == AtomicInteger.class) {
            return adapter.createAtomicInteger(text);
         }
         if(actual == BigDecimal.class) {
            return adapter.createBigDecimal(text);
         }
         if(actual == BigInteger.class) {
            return adapter.createBigInteger(text);
         }
         if(actual == Character.class) {
            return adapter.createCharacter(text);
         }
      } catch(Exception e) {
         throw new InternalStateException("Could not convert '" + text + "' to " + actual, e);
      }
      throw new InternalArgumentException("Could not convert '" + text + "' to " + actual);
   }
   
   protected Object convert(Class type, Number number) {
      Class actual = promoter.promote(type);
      Class real = number.getClass();
      
      try {
         if(actual == real) {
            return number;
         }
         if(actual == Number.class) {
            return number;
         }
         if(actual == Double.class) {
            return adapter.createDouble(number);
         }
         if(actual == Float.class) {
            return adapter.createFloat(number);
         }
         if(actual == Integer.class) {
            return adapter.createInteger(number);
         }
         if(actual == Long.class) {
            return adapter.createLong(number);
         }
         if(actual == Byte.class) {
            return adapter.createByte(number);
         }
         if(actual == Short.class) {
            return adapter.createShort(number);
         }
         if(actual == AtomicLong.class) {
            return adapter.createAtomicLong(number);
         }
         if(actual == AtomicInteger.class) {
            return adapter.createAtomicInteger(number);
         }
         if(actual == BigDecimal.class) {
            return adapter.createBigDecimal(number);
         }
         if(actual == BigInteger.class) {
            return adapter.createBigInteger(number);
         }
         if(actual == Character.class) {
            return adapter.createCharacter(number);
         }
      } catch(Exception e) {
         throw new InternalStateException("Could not convert '" + number + "' to " + type);
      }
      throw new InternalArgumentException("Could not convert '" + number + "' to " + actual);
   }
   
   public Object assign(Object value) throws Exception {
      return convert(value);
   }
   
   public abstract Object convert(Object value) throws Exception;
   public abstract Score score(Type type) throws Exception;
   public abstract Score score(Object type) throws Exception;
}