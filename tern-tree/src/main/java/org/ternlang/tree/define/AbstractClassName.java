package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.ABSTRACT;
import static org.ternlang.core.ModifierType.CLASS;

import org.ternlang.core.scope.Scope;
import org.ternlang.tree.constraint.ClassName;
import org.ternlang.tree.constraint.GenericList;
import org.ternlang.tree.literal.TextLiteral;

public class AbstractClassName extends ClassName {

   public AbstractClassName(TextLiteral literal, GenericList generics) {
      super(literal, generics);
   }
   
   @Override
   public int getModifiers(Scope scope) throws Exception{
      return ABSTRACT.mask | CLASS.mask;
   }
}
