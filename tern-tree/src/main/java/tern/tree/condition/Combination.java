package tern.tree.condition;

import static tern.core.constraint.Constraint.BOOLEAN;
import static tern.core.variable.BooleanValue.FALSE;
import static tern.core.variable.BooleanValue.TRUE;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class Combination extends Evaluation {
   
   private final ConditionalOperator operator;
   private final Evaluation right;
   private final Evaluation left;
   
   public Combination(Evaluation left) {
      this(left, null, null);
   }
   
   public Combination(Evaluation left, StringToken operator, Evaluation right) {
      this.operator = ConditionalOperator.resolveOperator(operator);
      this.right = right;
      this.left = left;
   }

   @Override
   public void define(Scope scope) throws Exception { 
      left.define(scope);
      
      if(right != null) {
         right.define(scope);
      }
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint context) throws Exception {
      left.compile(scope, null);
      
      if(right != null) {
         right.compile(scope, null);
      }
      return BOOLEAN;
   }
   
   @Override
   public Value evaluate(Scope scope, Value context) throws Exception { 
      Value first = evaluate(scope, left);
      
      if(first == TRUE) {
         if(operator != null) {
            if(operator.isAnd()) {
               return evaluate(scope, right);
            }
         }
      } else {
         if(operator != null) {
            if(operator.isOr()) {
               return evaluate(scope, right);
            }
         }
      }
      return first;
   }
   
   private Value evaluate(Scope scope, Evaluation evaluation) throws Exception { 
      Value value = evaluation.evaluate(scope, null);
      Object result = value.getValue();
      
      if(BooleanChecker.isTrue(result)) {
         return TRUE;
      }
      return FALSE;
   } 
}