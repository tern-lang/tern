package org.ternlang.core.scope.index;

import org.ternlang.core.ModifierType;
import org.ternlang.core.property.Property;
import org.ternlang.core.variable.Value;

public class LocalScopeChecker {
   
   public LocalScopeChecker() {
      super();
   }
   
   public boolean isValid(Value value){ 
      return value != null;
   }
   
   public boolean isGenerated(Value value) {
      if(value != null) {
         Object object = value.getValue();
         
         if(Property.class.isInstance(object)) {
            Property property = (Property)object;
            int modifiers = property.getModifiers();
            
            if(ModifierType.isFunction(modifiers)) {
               return true;  // don't allow generated properties
            }
         }
      }
      return false;
   }   
}
