package org.ternlang.tree.operation;

import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.math.NumberType;

public enum SignOperator {
   NONE(""){
      @Override
      public Value operate(Number value) {
         NumberType type = NumberType.resolveType(value);
         double decimal = value.doubleValue();
         
         return type.convert(decimal);
      }      
   },
   PLUS("+"){
      @Override
      public Value operate(Number value) {
         NumberType type = NumberType.resolveType(value);
         double decimal = value.doubleValue();
         
         return type.convert(+decimal);
      }      
   },
   MINUS("-"){
      @Override
      public Value operate(Number value) { 
         NumberType type = NumberType.resolveType(value);
         double decimal = value.doubleValue();
         
         return type.convert(-decimal);
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