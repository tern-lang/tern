package org.ternlang.tree.operation;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.literal.Literal;
import org.ternlang.tree.literal.NumberLiteral;

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