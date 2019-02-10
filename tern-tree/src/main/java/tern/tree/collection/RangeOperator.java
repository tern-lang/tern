package tern.tree.collection;

import tern.parse.StringToken;

public enum RangeOperator {
   DOT("..", true),
   TO("to", true),
   FROM("from", false);

   public final String operator;
   public final boolean forward;

   private RangeOperator(String operator, boolean forward) {
      this.operator = operator;
      this.forward = forward;
   }

   public boolean isForward() {
      return forward;
   }

   public boolean isReverse() {
      return !forward;
   }

   public static RangeOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();

         for(RangeOperator operator : VALUES) {
            if (operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }

   private static final RangeOperator[] VALUES = {
        DOT,
        TO,
        FROM
   };
}