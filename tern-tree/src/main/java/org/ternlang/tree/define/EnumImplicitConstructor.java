package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.CONSTANT;

import org.ternlang.core.Evaluation;
import org.ternlang.core.Statement;
import org.ternlang.core.function.Signature;
import org.ternlang.core.type.TypePart;
import org.ternlang.tree.constraint.EnumName;
import org.ternlang.tree.function.ParameterList;
import org.ternlang.tree.literal.TextLiteral;

public class EnumImplicitConstructor extends ImplicitConstructor {

   public EnumImplicitConstructor(TextLiteral name, ParameterList parameters) {
      super(name, null, parameters, CONSTANT.mask);
   }

   @Override
   protected TypeName construct(Evaluation name, Signature signature, TypePart[] parts) throws Exception {
      Statement statement = new ImplicitBody(signature);
      EnumConstructor constructor = new EnumConstructor(annotations, modifiers, parameters, statement);
      TypePart part = new ImplicitPart(constructor, parts);

      return new EnumName(name, part);
   }
}
