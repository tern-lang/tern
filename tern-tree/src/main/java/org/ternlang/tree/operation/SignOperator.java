package org.ternlang.tree.operation;

import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.math.NumericConverter;

public enum SignOperator {
   NONE(""){
      @Override
      public Value operate(Number value) {
         NumericConverter converter = NumericConverter.resolveConverter(value);      
         double decimal = value.doubleValue();
         
         return converter.convert(decimal);
      }      
   },
   PLUS("+"){
      @Override
      public Value operate(Number value) {
         NumericConverter converter = NumericConverter.resolveConverter(value);      
         double decimal = value.doubleValue();
         
         return converter.convert(+decimal);
      }      
   },
   MINUS("-"){
      @Override
      public Value operate(Number value) { 
         NumericConverter converter = NumericConverter.resolveConverter(value);      
         double decimal = value.doubleValue();
         
         return converter.convert(-decimal);
      }      
   };
   
   public final String operator;
   
   private SignOperator(String operator){
      this.operator = operator;
   }
   
   public abstract Value operate(Number right);   
   
   public static SignOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         
         for(SignOperator operator : VALUES) {
            if(operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return NONE;
   }  
   
   private static final SignOperator[] VALUES = {
      MINUS,
      PLUS,
      NONE
   };
}