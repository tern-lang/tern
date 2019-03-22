package org.ternlang.core.convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ConstraintAdapter {

   public Double createDouble(String text) {
      return Double.parseDouble(text);
   }
   
   public Float createFloat(String text) {
      return (float)Double.parseDouble(text);
   }
   
   public Integer createInteger(String text) {
      return (int)Double.parseDouble(text);
   }
   
   public Long createLong(String text) {
      return (long)Double.parseDouble(text);
   }
   
   public Short createShort(String text) {
      return (short)Double.parseDouble(text);
   }
   
   public Byte createByte(String text) {
      return (byte)Double.parseDouble(text);
   }
   
   public BigDecimal createBigDecimal(String text) {
      return new BigDecimal(text);
   }
   
   public BigInteger createBigInteger(String text) {
      return new BigDecimal(text).toBigInteger();
   }
   
   public AtomicLong createAtomicLong(String text) {
      long value = (long)Double.parseDouble(text);
      return new AtomicLong(value);
   }
   
   public AtomicInteger createAtomicInteger(String text) {
      int value = (int)Double.parseDouble(text);
      return new AtomicInteger(value);
   }
   
   public Boolean createBoolean(String text) {
      return Boolean.parseBoolean(text);
   }
   
   public Character createCharacter(String text) {
      return text.charAt(0);
   }
   
   public Double createDouble(Number number) {
      return number.doubleValue();
   }
   
   public Float createFloat(Number number) {
      return number.floatValue();
   }
   
   public Integer createInteger(Number number) {
      return number.intValue();
   }
   
   public Long createLong(Number number) {
      return number.longValue();
   }
   
   public Short createShort(Number number) {
      return number.shortValue();
   }
   
   public Byte createByte(Number number) {
      return number.byteValue();
   }
   
   public BigDecimal createBigDecimal(Number number) {
      if(!BigDecimal.class.isInstance(number)) {
         String value = number.toString();
         return createBigDecimal(value);
      }
      return (BigDecimal)number;
   }
   
   public BigInteger createBigInteger(Number number) {
      if(!BigInteger.class.isInstance(number)) {
         BigDecimal value = createBigDecimal(number);
         return value.toBigInteger();
      }
      return (BigInteger)number;
   }
   
   public AtomicLong createAtomicLong(Number number) {
      long value = number.longValue();
      return new AtomicLong(value);
   }
   
   public AtomicInteger createAtomicInteger(Number number) {
      int value = number.intValue();
      return new AtomicInteger(value);
   }
   
   public Character createCharacter(Number number) {
      char value = (char)number.shortValue();
      return Character.valueOf(value);
   }
}