package org.ternlang.core.type.extend;

import static java.math.RoundingMode.CEILING;
import static java.math.RoundingMode.FLOOR;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalExtension implements NumberExtension<BigDecimal> {

   public BigDecimalExtension() {
      super();
   }

   @Override
   public BigDecimal abs(BigDecimal number) {
      return number.abs();
   }

   @Override
   public BigDecimal ceil(BigDecimal number) {
      return number.setScale(0, CEILING);
   }

   @Override
   public BigDecimal floor(BigDecimal number) {
      return number.setScale(0, FLOOR);
   }

   @Override
   public BigInteger round(BigDecimal number) {
      return number.toBigInteger();
   }

   @Override
   public BigDecimal round(BigDecimal number, int places) {
      if(places > 0) {
         return number.setScale(places, HALF_UP);
      }
      return number.setScale(0, HALF_UP);
   }
}
