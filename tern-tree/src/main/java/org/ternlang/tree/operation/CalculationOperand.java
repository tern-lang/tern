package org.ternlang.tree.operation;

import org.ternlang.core.Evaluation;
import org.ternlang.tree.math.NumericOperator;

public class CalculationOperand implements CalculationPart {   
   
   private final Evaluation evaluation;
   
   public CalculationOperand(Evaluation evaluation) {
      this.evaluation = evaluation;
   }
   
   @Override
   public Evaluation getEvaluation(Evaluation left, Evaluation right){
      return evaluation;
   }

   @Override
   public NumericOperator getOperator() {
      return null;
   }
}