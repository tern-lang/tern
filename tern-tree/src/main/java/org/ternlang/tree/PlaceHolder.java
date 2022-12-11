package org.ternlang.tree;

import static org.ternlang.core.variable.Constant.STRING;
import static org.ternlang.core.Expansion.CLOSURE;

import org.ternlang.core.Expansion;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.literal.Literal;

public class PlaceHolder extends Literal {

   private final StringToken token;

   public PlaceHolder(StringToken token) {
      this.token = token;
   }

   @Override
   public Expansion expansion(Scope scope) throws Exception {
      return CLOSURE;
   }

   @Override
   protected Literal.LiteralValue create(Scope scope) throws Exception {
      String text = token.getValue();

      if(text == null) {
         throw new InternalStateException("Place holder value was null");
      }
      return new LiteralValue(text, STRING);
   }
}
