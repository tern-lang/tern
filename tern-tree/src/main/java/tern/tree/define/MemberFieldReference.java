package tern.tree.define;

import tern.core.ModifierType;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.tree.NameReference;
import tern.tree.literal.TextLiteral;

public class MemberFieldReference {
   
   private final NameReference identifier;
   
   public MemberFieldReference(TextLiteral identifier) {
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
