package tern.core.type.extend;

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
}
