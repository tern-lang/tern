package org.ternlang.tree;

import static org.ternlang.core.ModifierType.*;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.literal.TextLiteral;

public class Global extends Declaration {

   public Global(TextLiteral identifier) {
      this(identifier, null, null);
   }

   public Global(TextLiteral identifier, Constraint constraint) {
      this(identifier, constraint, null);
   }

   public Global(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }

   public Global(TextLiteral identifier, Constraint constraint, Evaluation value) {
      super(identifier, constraint, value);
   }

   public Value compile(Scope scope, int modifiers) throws Exception {
      return super.compile(scope, modifiers | STATIC.mask | PUBLIC.mask);
   }

   public Value declare(Scope scope, int modifiers) throws Exception {
      return super.declare(scope, modifiers | STATIC.mask | PUBLIC.mask);
   }
}
