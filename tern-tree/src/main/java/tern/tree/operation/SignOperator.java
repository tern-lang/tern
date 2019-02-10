package tern.tree.operation;

import tern.core.variable.Value;
import tern.parse.StringToken;
import tern.tree.math.NumericConverter;

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