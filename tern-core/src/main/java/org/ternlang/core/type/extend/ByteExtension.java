package org.ternlang.core.type.extend;

public class ByteExtension implements NumberExtension<Byte> {

   public ByteExtension() {
      super();
   }

   @Override
   public Integer abs(Byte number) {
      return Math.abs(number);
   }

   @Override
   public Byte ceil(Byte number) {
      return number;
   }

   @Override
   public Byte floor(Byte number) {
      return number;
   }

   @Override
   public Byte round(Byte number) {
      return number;
   }

   @Override
   public Byte round(Byte number, int places) {
      return number;
   }

   public String toBinaryString(Byte number) {
      return Integer.toBinaryString(number);
   }

   public String toHexString(Byte number) {
      return Integer.toHexString(number);
   }

   public String toOctalString(Byte number) {
      return Integer.toOctalString(number);
   }

   public String toString(Byte number, int radix) {
      return Integer.toString(number, radix);
   }
}
