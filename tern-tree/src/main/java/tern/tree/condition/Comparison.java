package tern.tree.condition;

import static tern.core.constraint.Constraint.BOOLEAN;
import static tern.core.variable.Value.NULL;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class Comparison extends Evaluation {   
   
   private final RelationalOperator operator;
   private final Evaluation left;
   private final Evaluation right;
   
   public Comparison(Evaluation left) {
      this(left, null, null);
   }
   
   public Comparison(Evaluation left, StringToken operator, Evaluation right) {
      this.operator = RelationalOperator.resolveOperator(operator);
      this.left = left;
      this.right = right;
   }

   @Override
   public void define(Scope scope) throws Exception {
      if(right != null) {
         right.define(scope);
      }
      left.define(scope);
   }  
   
   @Override
   public Constraint compile(Scope scope, Constraint context) throws Exception {
      if(right != null) {
         right.compile(scope, null);
      }
      left.compile(scope, null);
      return BOOLEAN;
   }
   
   @Override
   public Value evaluate(Scope scope, Value context) throws Exception {
      if(right != null) {
         Value leftResult = left.evaluate(scope, NULL);
         Value rightResult = right.evaluate(scope, NULL);
         
         return operator.operate(scope, leftResult, rightResult);
      }
      return left.evaluate(scope, NULL);
   }      
}