package tern.core.type.extend;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongExtension implements NumberExtension<AtomicLong> {

   public AtomicLongExtension() {
      super();
   }

   @Override
   public AtomicLong abs(AtomicLong number) {
      long value = number.get();
      long absolute = Math.abs(value);

      return new AtomicLong(absolute);
   }

   @Override
   public AtomicLong ceil(AtomicLong number) {
      return number;
   }

   @Override
   public AtomicLong floor(AtomicLong number) {
      return number;
   }

   @Override
   public AtomicLong round(AtomicLong number) {
      return number;
   }

   @Override
   public AtomicLong round(AtomicLong number, int places) {
      return number;
   }
}
