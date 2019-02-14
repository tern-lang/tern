package org.ternlang.tree;

import org.ternlang.core.ModifierType;
import org.ternlang.parse.StringToken;

public class Modifier {
   
   private ModifierType type;
   private StringToken token;

   public Modifier(StringToken token) {
      this.token = token;
   }

   public ModifierType getType() {
      String name = token.getValue();

      if(type == null) {
         type =  ModifierType.resolveModifier(name);
      }
      return type;
   } 

}