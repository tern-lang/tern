package org.ternlang.core.variable.index;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeBinder;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.bind.VariableFinder;

public class TypeLocalPointer implements VariablePointer<Scope> {
   
   private final TypeInstancePointer pointer;
   private final ScopeBinder binder;
   private final String name;
   
   public TypeLocalPointer(VariableFinder finder, String name) {
      this.pointer = new TypeInstancePointer(finder, name);
      this.binder = new ScopeBinder();
      this.name = name;
   }
   
   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      Scope instance = binder.bind(scope, scope);
      ScopeState state = instance.getState();
      Value value = state.getValue(name);
      
      if(value == null) {
         return pointer.getConstraint(instance, left);
      }
      return value.getConstraint();
   }
   
   @Override
   public Value getValue(Scope scope, Scope left) {
      Scope instance = binder.bind(scope, scope);
      ScopeState state = instance.getState();
      Value value = state.getValue(name);
      
      if(value == null) {
         return pointer.getValue(instance, instance);
      }
      return value;
   }
}