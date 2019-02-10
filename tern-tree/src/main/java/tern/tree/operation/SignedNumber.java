package tern.tree.operation;

import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.parse.StringToken;
import tern.tree.literal.Literal;
import tern.tree.literal.NumberLiteral;

public class SignedNumber extends Literal {
   
   private SignOperator operator;
   private NumberLiteral literal;
   
   public SignedNumber(NumberLiteral literal) {
      this(null, literal);
   }
   
   public SignedNumber(StringToken sign, NumberLiteral literal) {
      this.operator = SignOperator.resolveOperator(sign);
      this.literal = literal;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      Value value = literal.evaluate(scope, null);
      Number number = value.getValue();
      
      if(number == null) {
         throw new InternalStateException("Number value was null");
      }
      Value result = operator.operate(number);
      Constraint constraint = result.getConstraint();
      Number signed = result.getValue();
      
      return new LiteralValue(signed, constraint);           
   }
}