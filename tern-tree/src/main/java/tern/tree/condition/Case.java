package tern.tree.condition;

import tern.core.Evaluation;
import tern.core.Statement;

public interface Case {
   Evaluation getEvaluation();
   Statement getStatement();
}