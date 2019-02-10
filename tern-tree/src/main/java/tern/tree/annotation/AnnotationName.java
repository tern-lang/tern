package tern.tree.annotation;

import tern.core.Evaluation;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.tree.literal.TextLiteral;

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