package tern.tree.define;

import tern.core.Evaluation;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.tree.literal.TextLiteral;

public class EnumKey extends Evaluation {
   
   protected final TextLiteral literal;
   
   public EnumKey(TextLiteral literal) {
      this.literal = literal;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception{
      Value value = literal.evaluate(scope, left);
      String name = value.getValue();

      return Value.getTransient(name);
   }  
}