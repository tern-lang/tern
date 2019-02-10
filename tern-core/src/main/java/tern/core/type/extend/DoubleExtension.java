package tern.core.type.extend;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

public class DoubleExtension implements NumberExtension<Double> {

   public DoubleExtension() {
      super();
   }

   @Override
   public Double abs(Double number) {
      return Math.abs(number);
   }

   @Override
   public Double ceil(Double number) {
      return Math.ceil(number);
   }

   @Override
   public Double floor(Double number) {
      return Math.floor(number);
   }

   @Override
   public Long round(Double number) {
      return Math.round(number);
   }

   @Override
   public Double round(Double number, int places) {
      BigDecimal value = BigDecimal.valueOf(number);

      if(places > 0) {
         return value.setScale(places, HALF_UP).doubleValue();
      }
      return round(number).doubleValue();
   }
}
