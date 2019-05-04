package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.ArgumentList;

public class AnyConstructor extends TypePart {
   
   private final ArgumentList arguments;
   private final TypeState state;
   
   public AnyConstructor(ArgumentList arguments) {
      this.state = new AnyState();
      this.arguments = arguments;
   }

   @Override
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      if(arguments != null) {
         return new SuperState(arguments, type);
      }
      return state;
   }
}