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
import org.ternlang.tree.math.NumberOperator;

public enum AssignmentOperator {
   EQUAL(NumberOperator.REPLACE, "="),
   PLUS_EQUAL(NumberOperator.PLUS, "+="),
   MINUS_EQUAL(NumberOperator.MINUS, "-="),
   POWER_EQUAL(NumberOperator.POWER, "**="),
   MLTIPLY_EQUAL(NumberOperator.MULTIPLY, "*="),
   MODULUS_EQUAL(NumberOperator.MODULUS, "%="),
   DIVIDE_EQUAL(NumberOperator.DIVIDE,"/="),
   AND_EQUAL(NumberOperator.AND, "&="),
   OR_EQUAL(NumberOperator.OR, "|="),
   XOR_EQUAL(NumberOperator.XOR, "^="),
   SHIFT_RIGHT_EQUAL(NumberOperator.SHIFT_RIGHT, ">>="),
   SHIFT_LEFT_EQUAL(NumberOperator.SHIFT_LEFT, "<<="),
   UNSIGNED_SHIFT_RIGHT_EQUAL(NumberOperator.UNSIGNED_SHIFT_RIGHT, ">>>=");
   
   private final NumberOperator operator;
   private final String symbol;
   
   private AssignmentOperator(NumberOperator operator, String symbol) {
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