package org.ternlang.tree;

import org.ternlang.core.Evaluation;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.closure.Closure;
import org.ternlang.tree.closure.ClosureParameterList;
import org.ternlang.tree.constraint.GenericList;
import org.ternlang.tree.function.ParameterDeclaration;
import org.ternlang.tree.literal.TextLiteral;
import org.ternlang.tree.reference.GenericArgumentList;

import static org.ternlang.core.Reserved.PLACE_HOLDER;

public class ClosureExpansion implements Expansion {

   private final AnnotationList annotations;
   private final ModifierList modifiers;
   private final GenericList generics;
   private final TextLiteral holder;
   private final StringToken token;
   private final Module module;
   private final Path path;
   private final int line;

   public ClosureExpansion(Module module, Path path, int line) {
      this.token = new StringToken(PLACE_HOLDER);
      this.holder = new TextLiteral(token);
      this.modifiers = new ModifierList();
      this.generics = new GenericArgumentList();
      this.annotations = new AnnotationList();
      this.module = module;
      this.path = path;
      this.line = line;
   }

   public Evaluation expand(Scope scope, Evaluation argument) throws Exception {
      if(argument.expansion(scope)) {
         ParameterDeclaration declaration = new ParameterDeclaration(annotations, modifiers, holder);
         ClosureParameterList parameters = new ClosureParameterList(declaration);
         Closure closure = new Closure(modifiers, generics, parameters, argument);

         return closure.compile(module, path, line);
      }
      return argument;
   }
}
