package org.ternlang.core.type.extend;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

public class FloatExtension implements NumberExtension<Float> {

   public FloatExtension() {
      super();
   }

   @Override
   public Float abs(Float number) {
      return Math.abs(number);
   }

   @Override
   public Double ceil(Float number) {
      return Math.ceil(number);
   }

   @Override
   public Double floor(Float number) {
      return Math.floor(number);
   }

   @Override
   public Integer round(Float number) {
      return Math.round(number);
   }

   @Override
   public Double round(Float number, int places) {
      BigDecimal value = BigDecimal.valueOf(number);

      if(places > 0) {
         return value.setScale(places, HALF_UP).doubleValue();
      }
      return round(number).doubleValue();
   }
}
