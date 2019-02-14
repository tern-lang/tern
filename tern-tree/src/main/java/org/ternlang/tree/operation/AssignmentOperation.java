package org.ternlang.tree.operation;

import org.ternlang.core.convert.StringBuilder;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

public class AssignmentOperation {

   private final AssignmentOperator operator;

   public AssignmentOperation(StringToken operator) {
      this.operator = AssignmentOperator.resolveOperator(operator);
   }

   public Value operate(Scope scope, Value left, Value right) throws Exception {
      if(operator != AssignmentOperator.EQUAL) {
         Object leftValue = left.getValue();

         if(!Number.class.isInstance(leftValue)) {
            Object rightValue = right.getValue();

            if(operator != AssignmentOperator.PLUS_EQUAL) {
               throw new InternalStateException("Operator " + operator + " is illegal");
            }
            String leftText = StringBuilder.create(scope, leftValue);
            String rightText = StringBuilder.create(scope, rightValue);
            String text = leftText.concat(rightText);

            left.setValue(text);
            return left;
         }
      }
      return operator.operate(scope, left, right);
   }
}
