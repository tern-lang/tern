package org.ternlang.tree;

import org.ternlang.core.ModifierType;
import org.ternlang.core.error.InternalStateException;

public class ModifierList extends ModifierData {

   private final Modifier[] modifiers;
   
   public ModifierList(Modifier... modifiers){
      this.modifiers = modifiers;
   }

   @Override
   public int getModifiers() {
      int mask = 0;
      
      for(Modifier modifier : modifiers) {        
         ModifierType type = modifier.getType();
         
         if(type != null) {
            if((mask & type.mask) == type.mask) {
               throw new InternalStateException("Modifier '" + type + "' declared twice");
            }
            mask |= type.mask;
         }
      }
      return mask;
   }

}