package org.ternlang.tree.literal;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.scope.Scope;
import org.ternlang.parse.StringToken;

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