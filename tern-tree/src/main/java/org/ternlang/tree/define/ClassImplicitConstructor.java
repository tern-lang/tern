package org.ternlang.tree.define;

import org.ternlang.core.Evaluation;
import org.ternlang.core.Statement;
import org.ternlang.core.function.Signature;
import org.ternlang.core.type.TypePart;
import org.ternlang.tree.constraint.ClassName;
import org.ternlang.tree.constraint.GenericList;
import org.ternlang.tree.function.ParameterList;
import org.ternlang.tree.literal.TextLiteral;

public class ClassImplicitConstructor extends ImplicitConstructor {

   public ClassImplicitConstructor(TextLiteral name, ParameterList parameters) {
      super(name, parameters);
   }

   public ClassImplicitConstructor(TextLiteral name, GenericList generics, ParameterList parameters) {
      super(name, generics, parameters);
   }

   @Override
   protected TypeName construct(Evaluation name, GenericList generics, Signature signature, TypePart[] parts) throws Exception {
      Statement statement = new ImplicitBody(signature);
      ClassConstructor constructor = new ClassConstructor(annotations, modifiers, parameters, statement);
      TypePart part = new ImplicitPart(constructor, parts);

      return new ClassName(name, generics, part);
   }
}
