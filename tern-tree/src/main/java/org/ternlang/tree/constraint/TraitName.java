package org.ternlang.tree.constraint;

import static org.ternlang.core.ModifierType.ABSTRACT;
import static org.ternlang.core.ModifierType.TRAIT;

import org.ternlang.core.scope.Scope;
import org.ternlang.tree.literal.TextLiteral;

public class TraitName extends ClassName {

   public TraitName(TextLiteral literal, GenericList generics) {
      super(literal, generics);
   } 
   
   @Override
   public int getModifiers(Scope scope) throws Exception{
      return TRAIT.mask | ABSTRACT.mask;
   }
}