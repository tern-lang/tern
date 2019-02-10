package tern.tree;

import tern.core.ModifierType;
import tern.parse.StringToken;

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