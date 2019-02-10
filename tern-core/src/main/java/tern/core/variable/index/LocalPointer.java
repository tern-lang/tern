package tern.core.variable.index;

import java.util.concurrent.atomic.AtomicReference;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.variable.Value;
import tern.core.variable.bind.VariableFinder;
import tern.core.variable.bind.VariableResult;

public class LocalPointer implements VariablePointer<Object> {
   
   private final AtomicReference<VariableResult> reference;
   private final VariableFinder finder;
   private final String name;
   
   public LocalPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<VariableResult>();
      this.finder = finder;
      this.name = name;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         ScopeState state = scope.getState();
         Value variable = state.getValue(name);
         
         if(variable == null) { 
            VariableResult match = finder.findType(scope, name);
            
            if(match != null) {
               return match.getConstraint(left); 
            }
            return null;
         }
         return variable.getConstraint();
      }
      return result.getConstraint(left);
   }
   
   @Override
   public Value getValue(Scope scope, Object left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         ScopeState state = scope.getState();
         Value variable = state.getValue(name);
         
         if(variable == null) { 
            VariableResult match = finder.findType(scope, name);
            
            if(match != null) {
               reference.set(match);
               return match.getValue(left);
            }
         }
         return variable;
      }
      return result.getValue(left);
   }
}