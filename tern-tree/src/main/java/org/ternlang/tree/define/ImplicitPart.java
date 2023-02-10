package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.STATIC;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;

public class ImplicitPart extends TypePart {

   private final TypePart constructor;
   private final TypePart[] parts;

   public ImplicitPart(TypePart constructor, TypePart[] parts) {
      this.constructor = constructor;
      this.parts = parts;
   }

   @Override
   public void create(TypeBody body, Type type, Scope scope) throws Exception {
      for (TypePart part : parts) {
         part.create(body, type, scope);
      }
      constructor.create(body, type, scope);
   }

   @Override
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      TypeStateCollector collector = new TypeStateCollector(STATIC);

      for (TypePart part : parts) {
         TypeState state = part.define(body, type, scope);

         if (state != null) {
            collector.update(state);
         }
      }
      if (constructor != null) {
         TypeState state = constructor.define(body, type, scope);

         if (state != null) {
            collector.update(state);
         }
      }
      return collector;
   }
}
