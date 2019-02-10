package tern.tree.literal;

import static tern.core.variable.Constant.BOOLEAN;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.parse.StringToken;

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