package org.ternlang.core;

import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.variable.Value.NULL;
import static org.ternlang.core.Expansion.NORMAL;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public abstract class Evaluation{

   public void define(Scope scope) throws Exception {}

   public Expansion expansion(Scope scope) throws Exception {
      return NORMAL;
   }

   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return NONE;
   }

   public Value evaluate(Scope scope, Value left) throws Exception {
      return NULL;
   }
}