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

   public Character toCharacter(Integer number) {
      return (char)number.longValue();
   }

   public String toBinaryString(Long number) {
      return Long.toBinaryString(number);
   }

   public String toHexString(Long number) {
      return Long.toHexString(number);
   }

   public String toOctalString(Long number) {
      return Long.toOctalString(number);
   }

   public String toString(Long number, int radix) {
      return Long.toString(number, radix);
   }
}
