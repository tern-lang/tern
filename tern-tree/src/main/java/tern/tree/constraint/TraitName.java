package tern.tree.constraint;

import static tern.core.ModifierType.ABSTRACT;
import static tern.core.ModifierType.TRAIT;

import tern.core.scope.Scope;
import tern.tree.literal.TextLiteral;

public class TraitName extends ClassName {

   public TraitName(TextLiteral literal, GenericList generics) {
      super(literal, generics);
   } 
   
   @Override
   public int getModifiers(Scope scope) throws Exception{
      return TRAIT.mask | ABSTRACT.mask;
   }
}