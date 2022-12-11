package org.ternlang.tree.operation;

import static org.ternlang.core.constraint.Constraint.STRING;
import static org.ternlang.core.variable.Value.NULL;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.StringBuilder;
import org.ternlang.core.convert.TypeInspector;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.math.NumberChecker;
import org.ternlang.tree.math.NumberOperator;

public class CalculationOperation extends Evaluation {

   private final TypeInspector inspector;
   private final NumberOperator operator;
   private final NumberChecker checker;
   private final Evaluation left;
   private final Evaluation right;
   
   public CalculationOperation(NumberOperator operator, Evaluation left, Evaluation right) {
      this.inspector = new TypeInspector();
      this.checker = new NumberChecker();
      this.operator = operator;
      this.left = left;
      this.right = right;
   }

   @Override
   public boolean expansion(Scope scope) throws Exception {
      return left.expansion(scope) || right.expansion(scope);
   }

   @Override
   public void define(Scope scope) throws Exception {
      left.define(scope);
      right.define(scope);
   }

   @Override
   public Constraint compile(Scope scope, Constraint context) throws Exception {
      Constraint leftResult = left.compile(scope, null);
      Constraint rightResult = right.compile(scope, null);
      Type leftType = leftResult.getType(scope);
      Type rightType = rightResult.getType(scope);

      if(operator == NumberOperator.PLUS) {
         if(!checker.isNumeric(leftType) && inspector.isType(leftType)) { // typed but not a number
            return STRING;
         }
         if(!checker.isNumeric(rightType) && inspector.isType(rightType)) {
            return STRING;
         }
      }
      return operator.compile(leftType, rightType);
   }

   @Override
   public Value evaluate(Scope scope, Value context) throws Exception {
      Value leftResult = left.evaluate(scope, NULL);
      Value rightResult = right.evaluate(scope, NULL);
      
      if(operator == NumberOperator.PLUS) {
         Object leftValue = leftResult.getValue();
         Object rightValue = rightResult.getValue();
         
         if(!checker.isBothNumeric(leftValue, rightValue)) {
            String leftText = StringBuilder.create(scope, leftValue);
            String rightText = StringBuilder.create(scope, rightValue);            
            String text = leftText.concat(rightText);
            
            return Value.getTransient(text);
         }
      }
      return operator.operate(leftResult, rightResult);
   }
}
