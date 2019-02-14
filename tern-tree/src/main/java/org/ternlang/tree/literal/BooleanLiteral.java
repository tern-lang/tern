package org.ternlang.tree.literal;

import static org.ternlang.core.variable.Constant.BOOLEAN;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.parse.StringToken;

public class BooleanLiteral extends Literal {
   
   private StringToken token;
   
   public BooleanLiteral(StringToken token) {
      this.token = token;
   }
 
   @Override
   protected LiteralValue create(Scope scope) throws Exception{
      String text = token.getValue();
      
      if(text == null) {
         throw new InternalStateException("Boolean value is null");
      }
      Boolean value = Boolean.parseBoolean(text);
      return new LiteralValue(value, BOOLEAN);
   }      
}