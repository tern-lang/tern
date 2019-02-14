package org.ternlang.core.type.extend;

public class ShortExtension implements NumberExtension<Short> {

   public ShortExtension() {
      super();
   }

   @Override
   public Integer abs(Short number) {
      return Math.abs(number);
   }

   @Override
   public Short ceil(Short number) {
      return number;
   }

   @Override
   public Short floor(Short number) {
      return number;
   }

   @Override
   public Short round(Short number) {
      return number;
   }

   @Override
   public Short round(Short number, int places) {
      return number;
   }
}
