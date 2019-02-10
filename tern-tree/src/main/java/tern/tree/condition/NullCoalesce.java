package tern.tree.condition;

import static tern.core.variable.Value.NULL;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class NullCoalesce extends Evaluation {

   private final Evaluation substitute;
   private final Evaluation evaluation;
   
   public NullCoalesce(Evaluation evaluation, Evaluation substitute) {
      this.evaluation = evaluation;
      this.substitute = substitute;
   }

   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
      substitute.define(scope);
   }   

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      substitute.compile(scope, null);
      return evaluation.compile(scope, null);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Value result = evaluation.evaluate(scope, NULL);
      Object value = result.getValue();
      
      if(value == null) {
         return substitute.evaluate(scope, left);
      } 
      return result;
   }
}