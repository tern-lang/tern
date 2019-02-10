package tern.tree.literal;

import static tern.core.variable.Constant.STRING;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.parse.StringToken;

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