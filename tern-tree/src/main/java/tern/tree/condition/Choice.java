package tern.tree.condition;

import static tern.core.variable.Value.NULL;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class Choice extends Evaluation {
   
   private final Evaluation condition;
   private final Evaluation positive;
   private final Evaluation negative;
   
   public Choice(Evaluation condition, Evaluation positive, Evaluation negative) {
      this.condition = condition;
      this.positive = positive;
      this.negative = negative;
   }

   @Override
   public void define(Scope scope) throws Exception {
      condition.define(scope);
      positive.define(scope);
      negative.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      condition.compile(scope, null);     
      negative.compile(scope, null);
      
      return positive.compile(scope, null);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Value result = condition.evaluate(scope, NULL);
      Object value = result.getValue();
      
      if(BooleanChecker.isTrue(value)) {
         return positive.evaluate(scope, left);
      } 
      return negative.evaluate(scope, left);
   }
}