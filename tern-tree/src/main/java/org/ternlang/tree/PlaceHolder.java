package org.ternlang.tree;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.literal.Literal;

import static org.ternlang.core.variable.Constant.STRING;

public class PlaceHolder extends Literal {

   private final StringToken token;

   public PlaceHolder(StringToken token) {
      this.token = token;
   }

   @Override
   public boolean expansion(Scope scope) throws Exception {
      return true;
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
