package tern.core;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;

public abstract class Statement {
   
   public void create(Scope scope) throws Exception {}
   
   public boolean define(Scope scope) throws Exception {
      return true; // executable?
   }
   
   public abstract Execution compile(Scope scope, Constraint returns) throws Exception;
}