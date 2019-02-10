package tern.core.type.extend;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExtension implements NumberExtension<AtomicInteger> {

   public AtomicIntegerExtension() {
      super();
   }

   @Override
   public AtomicInteger abs(AtomicInteger number) {
      int value = number.get();
      int absolute = Math.abs(value);

      return new AtomicInteger(absolute);
   }

   @Override
   public AtomicInteger ceil(AtomicInteger number) {
      return number;
   }

   @Override
   public AtomicInteger floor(AtomicInteger number) {
      return number;
   }

   @Override
   public AtomicInteger round(AtomicInteger number) {
      return number;
   }

   @Override
   public AtomicInteger round(AtomicInteger number, int places) {
      return number;
   }
}
