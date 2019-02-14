package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;

public class AnyConstructor extends TypePart {
   
   private final TypeState state;
   
   public AnyConstructor() {
      this.state = new AnyState();
   }

   @Override
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      return state;
   }
}