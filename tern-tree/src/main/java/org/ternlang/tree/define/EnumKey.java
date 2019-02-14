package org.ternlang.tree.define;

import org.ternlang.core.Evaluation;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.literal.TextLiteral;

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