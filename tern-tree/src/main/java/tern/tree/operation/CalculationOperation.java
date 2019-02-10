package tern.tree.operation;

import static tern.core.variable.Value.NULL;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.convert.StringBuilder;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.tree.math.NumericChecker;
import tern.tree.math.NumericOperator;

public class CalculationOperation extends Evaluation {

   private final NumericOperator operator;
   private final Evaluation left;
   private final Evaluation right;
   
   public CalculationOperation(NumericOperator operator, Evaluation left, Evaluation right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      left.define(scope);
      right.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint context) throws Exception {
      left.compile(scope, null);
      return right.compile(scope, null);
   }
   
   
   @Override
   public Value evaluate(Scope scope, Value context) throws Exception {
      Value leftResult = left.evaluate(scope, NULL);
      Value rightResult = right.evaluate(scope, NULL);
      
      if(operator == NumericOperator.PLUS) {
         Object leftValue = leftResult.getValue();
         Object rightValue = rightResult.getValue();
         
         if(!NumericChecker.isBothNumeric(leftValue, rightValue)) {
            String leftText = StringBuilder.create(scope, leftValue);
            String rightText = StringBuilder.create(scope, rightValue);            
            String text = leftText.concat(rightText);
            
            return Value.getTransient(text);
         }
      }
      return operator.operate(leftResult, rightResult);
   }
}