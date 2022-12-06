package org.ternlang.core.type.extend;

public class IntegerExtension implements NumberExtension<Integer> {

   public IntegerExtension() {
      super();
   }

   @Override
   public Integer abs(Integer number) {
      return Math.abs(number);
   }

   @Override
   public Integer ceil(Integer number) {
      return number;
   }

   @Override
   public Integer floor(Integer number) {
      return number;
   }

   @Override
   public Integer round(Integer number) {
      return number;
   }

   @Override
   public Integer round(Integer number, int places) {
      return number;
   }

   public Character toCharacter(Integer number) {
      return (char)number.intValue();
   }

   public String toBinaryString(Integer number) {
      return Integer.toBinaryString(number);
   }

   public String toHexString(Integer number) {
      return Integer.toHexString(number);
   }

   public String toOctalString(Integer number) {
      return Integer.toOctalString(number);
   }

   public String toString(Integer number, int radix) {
      return Integer.toString(number, radix);
   }
}
