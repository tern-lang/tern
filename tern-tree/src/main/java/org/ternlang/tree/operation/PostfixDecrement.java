package org.ternlang.tree.operation;

import org.ternlang.core.Evaluation;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.Token;
import org.ternlang.tree.math.NumericConverter;

public class PostfixDecrement extends NumericOperation {

   public PostfixDecrement(Evaluation evaluation, Token operator) {
      super(evaluation, operator);
   }

   @Override
   public Value evaluate(Scope scope, Value left) throws Exception { // this is rubbish
      Value reference = evaluation.evaluate(scope, left);
      Number number = reference.getNumber();
      NumericConverter converter = NumericConverter.resolveConverter(number);
      Value value = converter.decrement(number);
      Number result = value.getNumber();
      
      reference.setValue(result);

      return Value.getTransient(number);
   }
}