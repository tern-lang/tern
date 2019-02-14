package org.ternlang.tree.operation;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.ConstraintConverter;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.Score;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.math.NumericOperator;

public enum AssignmentOperator {
   EQUAL(NumericOperator.REPLACE, "="), 
   PLUS_EQUAL(NumericOperator.PLUS, "+="), 
   MINUS_EQUAL(NumericOperator.MINUS, "-="), 
   POWER_EQUAL(NumericOperator.POWER, "**="),    
   MLTIPLY_EQUAL(NumericOperator.MULTIPLY, "*="), 
   MODULUS_EQUAL(NumericOperator.MODULUS, "%="), 
   DIVIDE_EQUAL(NumericOperator.DIVIDE,"/="),  
   AND_EQUAL(NumericOperator.AND, "&="),    
   OR_EQUAL(NumericOperator.OR, "|="),
   XOR_EQUAL(NumericOperator.XOR, "^="),    
   SHIFT_RIGHT_EQUAL(NumericOperator.SHIFT_RIGHT, ">>="),
   SHIFT_LEFT_EQUAL(NumericOperator.SHIFT_LEFT, "<<="),
   UNSIGNED_SHIFT_RIGHT_EQUAL(NumericOperator.UNSIGNED_SHIFT_RIGHT, ">>>=");
   
   private final NumericOperator operator;
   private final String symbol;
   
   private AssignmentOperator(NumericOperator operator, String symbol) {
      this.operator = operator;
      this.symbol = symbol;
   }
   
   public Value operate(Scope scope, Value left, Value right) throws Exception {
      Constraint constraint = left.getConstraint();
      Type type = constraint.getType(scope);
      Value result = operator.operate(left, right);
      Object value = result.getValue();
      
      if(type != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         if(score.isInvalid()) {
            throw new InternalStateException("Illegal assignment to variable of type '" + type + "'");
         }
         if(value != null) {
            value = converter.assign(value);
         }
      }
      left.setValue(value);
      return left;
   }
   
   public static AssignmentOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         
         for(AssignmentOperator operator : VALUES) {
            if(operator.symbol.equals(value)) {
               return operator;
            }
         }
      }
      return EQUAL;
   }
   
   private static final AssignmentOperator[] VALUES = {
      EQUAL,
      PLUS_EQUAL,
      MINUS_EQUAL,
      POWER_EQUAL,   
      MLTIPLY_EQUAL,
      MODULUS_EQUAL, 
      DIVIDE_EQUAL,  
      AND_EQUAL,   
      OR_EQUAL,
      XOR_EQUAL,    
      SHIFT_RIGHT_EQUAL,
      SHIFT_LEFT_EQUAL,
      UNSIGNED_SHIFT_RIGHT_EQUAL
   };
}