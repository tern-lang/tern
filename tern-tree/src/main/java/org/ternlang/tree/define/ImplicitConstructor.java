package org.ternlang.tree.define;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.Reserved.TYPE_CLASS;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypePart;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.constraint.GenericList;
import org.ternlang.tree.function.ParameterList;
import org.ternlang.tree.literal.TextLiteral;

import java.util.List;

public abstract class ImplicitConstructor implements Compilation {

   protected final AnnotationList annotations;
   protected final ParameterList parameters;
   protected final ModifierList modifiers;
   protected final GenericList generics;
   protected final TextLiteral name;

   public ImplicitConstructor(TextLiteral name, ParameterList parameters) {
      this(name, null, parameters);
   }

   public ImplicitConstructor(TextLiteral name, GenericList generics, ParameterList parameters) {
      this.annotations = new AnnotationList();
      this.modifiers = new ModifierList();
      this.parameters = parameters;
      this.generics = generics;
      this.name = name;
   }

   @Override
   public TypeName compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Signature signature = parameters.create(scope, EMPTY_LIST, TYPE_CLASS);
      TypePart[] parts = declare(module, path, line);

      return construct(name, generics, signature, parts);
   }

   private TypePart[] declare(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Signature signature = parameters.create(scope, EMPTY_LIST, TYPE_CLASS);
      List<Parameter> parameters = signature.getParameters();
      int count = parameters.size();

      if (count > 1) {
         ImplicitFieldBuilder builder = new ImplicitFieldBuilder(module, path, line);
         TypePart[] declarations = new TypePart[count - 1];

         for (int i = 1; i < count; i++) {
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            String name = parameter.getName();

            declarations[i - 1] = builder.create(name, constraint);
         }
         return declarations;
      }
      return new TypePart[]{};
   }

   protected abstract TypeName construct(Evaluation name, GenericList generics, Signature signature, TypePart[] parts) throws Exception;

}
