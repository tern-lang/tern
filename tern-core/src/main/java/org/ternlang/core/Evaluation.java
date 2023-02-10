package org.ternlang.core;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.variable.Value.NULL;

public abstract class Evaluation{

   public boolean expansion(Scope scope) throws Exception {
      return false;
   }

   public void define(Scope scope) throws Exception {}

   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return NONE;
   }

   public Value evaluate(Scope scope, Value left) throws Exception {
      return NULL;
   }
}