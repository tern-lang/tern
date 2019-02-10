package tern.tree.function;

import static tern.core.variable.Constant.STRING;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.parse.StringToken;
import tern.tree.literal.Literal;

public class ParameterBlank extends Literal {
   
   private final StringToken token;
   
   public ParameterBlank(StringToken token) {
      this.token = token;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      String text = token.getValue();
      int hash = System.identityHashCode(this);
      
      if(text == null) {
         throw new InternalStateException("Text value was null");
      }
      return new LiteralValue(text + hash, STRING);
   }
}