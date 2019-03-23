package org.ternlang.tree.operation;

import org.ternlang.core.Evaluation;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.Token;
import org.ternlang.tree.math.NumberType;

public class PostfixIncrement extends NumberOperation {

   public PostfixIncrement(Evaluation evaluation, Token operator) {
      super(evaluation, operator);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception { // this is rubbish
      Value reference = evaluation.evaluate(scope, left);
      Number number = reference.getNumber();
      NumberType type = NumberType.resolveType(number);
      Value value = type.increment(number);
      Number result = value.getNumber();
      
      reference.setValue(result);
      
      return Value.getTransient(number);
   }
}