package org.ternlang.tree.operation;

import org.ternlang.core.Evaluation;
import org.ternlang.tree.math.NumericOperator;

public interface CalculationPart {
   Evaluation getEvaluation(Evaluation left, Evaluation right);
   NumericOperator getOperator();
}