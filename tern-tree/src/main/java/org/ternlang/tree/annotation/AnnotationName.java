package org.ternlang.tree.annotation;

import org.ternlang.core.Evaluation;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.literal.TextLiteral;

public class AnnotationName extends Evaluation {

   private final TextLiteral literal;
   
   public AnnotationName(TextLiteral literal) {
      this.literal = literal;
   }

   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      return literal.evaluate(scope, left);
   }
   
}