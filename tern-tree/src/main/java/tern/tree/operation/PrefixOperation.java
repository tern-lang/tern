package tern.tree.operation;

import static tern.core.variable.Value.NULL;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class PrefixOperation extends Evaluation {
   
   private final PrefixOperator operator;
   private final Evaluation evaluation;
   
   public PrefixOperation(StringToken operator, Evaluation evaluation) {
      this.operator = PrefixOperator.resolveOperator(operator);
      this.evaluation = evaluation;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return evaluation.compile(scope, left);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Value reference = evaluation.evaluate(scope, NULL);
      return operator.operate(reference);
   } 
}