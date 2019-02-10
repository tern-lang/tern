package tern.tree;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class Argument extends Evaluation{
   
   private final Evaluation evaluation;
   
   public Argument(Evaluation evaluation){
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
      return evaluation.evaluate(scope, left);
   }
}