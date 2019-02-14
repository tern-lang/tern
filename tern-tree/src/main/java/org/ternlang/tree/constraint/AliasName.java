package org.ternlang.tree.constraint;

import static org.ternlang.core.ModifierType.ALIAS;

import org.ternlang.core.scope.Scope;
import org.ternlang.tree.literal.TextLiteral;

public class AliasName extends ClassName {

   public AliasName(TextLiteral literal, GenericList generics) {
      super(literal, generics);
   }

   @Override
   public int getModifiers(Scope scope) throws Exception{
      return ALIAS.mask;
   }
}