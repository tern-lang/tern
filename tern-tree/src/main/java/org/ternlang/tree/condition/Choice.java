package org.ternlang.tree.condition;

import static org.ternlang.core.variable.Value.NULL;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

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