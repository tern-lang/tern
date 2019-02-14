package org.ternlang.tree.function;

import static org.ternlang.core.variable.Constant.STRING;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.literal.Literal;

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