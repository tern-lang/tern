package org.ternlang.core.type.extend;

import java.math.BigInteger;

public class BigIntegerExtension implements NumberExtension<BigInteger> {

   public BigIntegerExtension() {
      super();
   }

   @Override
   public BigInteger abs(BigInteger number) {
      return number.abs();
   }

   @Override
   public BigInteger ceil(BigInteger number) {
      return number;
   }

   @Override
   public BigInteger floor(BigInteger number) {
      return number;
   }

   @Override
   public BigInteger round(BigInteger number) {
      return number;
   }

   @Override
   public BigInteger round(BigInteger number, int places) {
      return number;
   }
}
