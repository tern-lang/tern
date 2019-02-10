package tern.tree.operation;

import tern.core.Evaluation;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.parse.Token;
import tern.tree.math.NumericConverter;

public class PrefixDecrement extends NumericOperation {

   public PrefixDecrement(Token operator, Evaluation evaluation) {
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

      return reference;
   }
}