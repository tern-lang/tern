package tern.core;

import static tern.core.constraint.Constraint.NONE;
import static tern.core.variable.Value.NULL;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public abstract class Evaluation{
   
   public void define(Scope scope) throws Exception {}
   
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return NONE;
   }

   public Value evaluate(Scope scope, Value left) throws Exception {
      return NULL;
   }
}