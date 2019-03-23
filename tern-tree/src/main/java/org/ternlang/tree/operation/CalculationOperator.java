package org.ternlang.tree.operation;

import static org.ternlang.tree.math.NumberOperator.COALESCE;

import org.ternlang.core.Evaluation;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.condition.NullCoalesce;
import org.ternlang.tree.math.NumberOperator;

public class CalculationOperator implements CalculationPart {   
   
   private final NumberOperator operator;
   
   public CalculationOperator(StringToken operator) {
      this.operator = NumberOperator.resolveOperator(operator);
   }
   
   @Override
   public Evaluation getEvaluation(Evaluation left, Evaluation right){
      if(operator == COALESCE) {
         return new NullCoalesce(left, right);
      }
      return new CalculationOperation(operator, left, right);
   }
   
   @Override
   public NumberOperator getOperator(){
      return operator;
   }
}