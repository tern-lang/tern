package org.ternlang.tree.literal;

import static org.ternlang.core.variable.Constant.STRING;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.parse.StringToken;

public class TextLiteral extends Literal {
   
   private final StringToken token;
   
   public TextLiteral(StringToken token) {
      this.token = token;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      String text = token.getValue();
      
      if(text == null) {
         throw new InternalStateException("Text value was null");
      }
      return new LiteralValue(text, STRING);
   }
}