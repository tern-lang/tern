package tern.tree;

import static tern.core.ModifierType.*;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.tree.literal.TextLiteral;

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
