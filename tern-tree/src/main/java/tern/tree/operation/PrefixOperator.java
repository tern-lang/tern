package tern.tree.operation;

import static tern.core.variable.BooleanValue.FALSE;
import static tern.core.variable.BooleanValue.TRUE;

import tern.core.variable.Value;
import tern.parse.StringToken;
import tern.tree.condition.BooleanChecker;
import tern.tree.math.NumericConverter;

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
         NumericConverter converter = NumericConverter.resolveConverter(value);   
         long number = value.longValue();
         
         return converter.convert(~number);
      }      
   },
   PLUS("+"){
      @Override
      public Value operate(Value right) {
         Number value = right.getNumber(); 
         NumericConverter converter = NumericConverter.resolveConverter(value);   
         double number = value.doubleValue();
         
         return converter.convert(+number);
      }      
   },
   MINUS("-"){
      @Override
      public Value operate(Value right) { 
         Number value = right.getNumber(); 
         NumericConverter converter = NumericConverter.resolveConverter(value);   
         double number = value.doubleValue();
         
         return converter.convert(-number);
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