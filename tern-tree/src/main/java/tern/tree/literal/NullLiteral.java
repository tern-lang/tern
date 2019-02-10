package tern.tree.literal;

import static tern.core.constraint.Constraint.NONE;

import tern.core.scope.Scope;
import tern.parse.StringToken;

public class NullLiteral extends Literal {
   
   private final StringToken token;

   public NullLiteral() {
      this(null);
   }
   
   public NullLiteral(StringToken token) {
      this.token = token;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      return new LiteralValue(null, NONE);
   }
}