package org.ternlang.tree.operation;

import static org.ternlang.core.variable.BooleanValue.FALSE;
import static org.ternlang.core.variable.BooleanValue.TRUE;

import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.condition.BooleanChecker;
import org.ternlang.tree.math.NumberType;

public enum PrefixOperator {
   NOT("!"){
      @Override
      public Value operate(Value right) { 
         Object result = right.getValue();         
         boolean value = !BooleanChecker.isTrue(result);
         
         return value ? TRUE : FALSE;
      }      
   }, 
   COMPLEMENT("~"){
      @Override
      public Value operate(Value right) {
         Number value = right.getNumber(); 
         NumberType type = NumberType.resolveType(value);
         long number = value.longValue();
         
         return type.convert(~number);
      }      
   },
   PLUS("+"){
      @Override
      public Value operate(Value right) {
         Number value = right.getNumber(); 
         NumberType type = NumberType.resolveType(value);
         double number = value.doubleValue();
         
         return type.convert(+number);
      }      
   },
   MINUS("-"){
      @Override
      public Value operate(Value right) { 
         Number value = right.getNumber(); 
         NumberType type = NumberType.resolveType(value);
         double number = value.doubleValue();
         
         return type.convert(-number);
      }      
   };
   
   public final String operator;
   
   private PrefixOperator(String operator){
      this.operator = operator;
   }
   
   public abstract Value operate(Value right);   
   
   public static PrefixOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         PrefixOperator[] operators = PrefixOperator.values();
         
         for(PrefixOperator operator : operators) {
            if(operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }  
}