package tern.core.type.extend;

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
}
