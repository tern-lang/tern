package org.ternlang.tree.operation;

import org.ternlang.core.Evaluation;
import org.ternlang.tree.math.NumberOperator;

public interface CalculationPart {
   Evaluation getEvaluation(Evaluation left, Evaluation right);
   NumberOperator getOperator();
}