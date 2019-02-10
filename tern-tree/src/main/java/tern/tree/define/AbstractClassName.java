package tern.tree.define;

import static tern.core.ModifierType.ABSTRACT;
import static tern.core.ModifierType.CLASS;

import tern.core.scope.Scope;
import tern.tree.constraint.ClassName;
import tern.tree.constraint.GenericList;
import tern.tree.literal.TextLiteral;

public class AbstractClassName extends ClassName {

   public AbstractClassName(TextLiteral literal, GenericList generics) {
      super(literal, generics);
   }
   
   @Override
   public int getModifiers(Scope scope) throws Exception{
      return ABSTRACT.mask | CLASS.mask;
   }
}
