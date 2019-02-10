package tern.tree.define;

import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypePart;
import tern.core.type.TypeState;

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