package tern.tree.operation;

import tern.core.Evaluation;
import tern.tree.math.NumericOperator;

public interface CalculationPart {
   Evaluation getEvaluation(Evaluation left, Evaluation right);
   NumericOperator getOperator();
}