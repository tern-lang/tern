package org.ternlang.tree.condition;

import org.ternlang.parse.StringToken;

public enum ConditionalOperator {
   AND("&&"),
   OR("||");
   
   private final String operator;
   
   private ConditionalOperator(String operator){
      this.operator = operator;
   }
   
  public boolean isAnd() {
     return this == AND;
  }
  
  public boolean isOr() {
     return this == OR;
  }
  
  public static ConditionalOperator resolveOperator(StringToken token){
     if(token != null) {
        String value = token.getValue();
        
        for(ConditionalOperator operator : VALUES) {
           if(operator.operator.equals(value)) {
              return operator;
           }
        }
     }
     return null;
  }
  
  private static final ConditionalOperator[] VALUES = {
     AND,
     OR
  };
}