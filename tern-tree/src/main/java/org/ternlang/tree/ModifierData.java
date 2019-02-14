package org.ternlang.tree;

import org.ternlang.core.ModifierType;
import org.ternlang.core.error.InternalStateException;

public class ModifierData {
   
   private final ModifierType[] types;
   
   public ModifierData(ModifierType... types) {
      this.types = types;
   }
   
   public int getModifiers(){
      int mask = 0;
      
      for(ModifierType type : types) {        
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