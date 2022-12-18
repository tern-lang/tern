package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.CONSTANT;
import static org.ternlang.core.ModifierType.VARIABLE;
import static org.ternlang.core.Reserved.TYPE_CLASS;
import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
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
import org.ternlang.tree.reference.GenericArgumentList;

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
      this.generics = generics != null ? generics : new GenericArgumentList();
      this.annotations = new AnnotationList();
      this.modifiers = new ModifierList();
      this.parameters = parameters;
      this.name = name;
   }

   @Override
   public TypeName compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Signature signature = parameters.expose(scope, TYPE_CLASS);
      TypePart[] parts = declare(module, path, line);

      return construct(name, generics, signature, parts);
   }

   private TypePart[] declare(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Signature signature = parameters.expose(scope, TYPE_CLASS);
      List<Parameter> list = signature.getParameters();
      int count = list.size();

      if (count > 1) {
         ImplicitFieldBuilder builder = new ImplicitFieldBuilder(module, path, line);
         TypePart[] declarations = new TypePart[count - 1];

         for (int i = 1; i < count; i++) {
            Parameter parameter = list.get(i);
            String name = parameter.getName();

            if(parameter.isConstant()) {
               declarations[i - 1] = builder.create(name, NONE, CONSTANT.mask);
            } else {
               declarations[i - 1] = builder.create(name, NONE, VARIABLE.mask);
            }
         }
         return declarations;
      }
      return new TypePart[]{};
   }

   protected abstract TypeName construct(Evaluation name, GenericList generics, Signature signature, TypePart[] parts) throws Exception;

}
