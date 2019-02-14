package org.ternlang.core.type.extend;

public class LongExtension implements NumberExtension<Long> {

   public LongExtension() {
      super();
   }

   @Override
   public Long abs(Long number) {
      return Math.abs(number);
   }

   @Override
   public Long ceil(Long number) {
      return number;
   }

   @Override
   public Long floor(Long number) {
      return number;
   }

   @Override
   public Long round(Long number) {
      return number;
   }

   @Override
   public Long round(Long number, int places) {
      return number;
   }
}
