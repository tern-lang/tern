package tern.tree.constraint;

import static tern.core.ModifierType.ALIAS;

import tern.core.scope.Scope;
import tern.tree.literal.TextLiteral;

public class AliasName extends ClassName {

   public AliasName(TextLiteral literal, GenericList generics) {
      super(literal, generics);
   }

   @Override
   public int getModifiers(Scope scope) throws Exception{
      return ALIAS.mask;
   }
}