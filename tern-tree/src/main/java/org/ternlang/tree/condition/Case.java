package org.ternlang.tree.condition;

import org.ternlang.core.Evaluation;
import org.ternlang.core.Statement;

public interface Case {
   Evaluation getEvaluation();
   Statement getStatement();
}