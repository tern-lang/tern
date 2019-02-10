package tern.tree.operation;

import static tern.tree.math.NumericOperator.COALESCE;

import tern.core.Evaluation;
import tern.parse.StringToken;
import tern.tree.condition.NullCoalesce;
import tern.tree.math.NumericOperator;

public class CalculationOperator implements CalculationPart {   
   
   private final NumericOperator operator;
   
   public CalculationOperator(StringToken operator) {
      this.operator = NumericOperator.resolveOperator(operator);
   }
   
   @Override
   public Evaluation getEvaluation(Evaluation left, Evaluation right){
      if(operator == COALESCE) {
         return new NullCoalesce(left, right);
      }
      return new CalculationOperation(operator, left, right);
   }
   
   @Override
   public NumericOperator getOperator(){
      return operator;
   }
}