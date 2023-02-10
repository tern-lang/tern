package org.ternlang.tree.define;

import org.ternlang.core.Evaluation;
import org.ternlang.core.ModifierType;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.NameReference;

public class MemberFieldReference {
   
   private final NameReference identifier;
   
   public MemberFieldReference(Evaluation identifier) {
      this.identifier = new NameReference(identifier);
   }
   
   public String getName(Type type, int modifiers) throws Exception {
      Class real = type.getType();
      Scope scope = type.getScope();
      String name = identifier.getName(scope);
      
      if(real == null) {
         int order = type.getOrder();
         
         if(ModifierType.isPrivate(modifiers) && ! ModifierType.isStatic(modifiers)) {
            if(order > 0) {
               return name + '@' + order; // private name
            }
         }
      }
      return name;
   }
}
