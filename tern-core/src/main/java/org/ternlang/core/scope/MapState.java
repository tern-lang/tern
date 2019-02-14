package org.ternlang.core.scope;

import java.util.Iterator;
import java.util.Set;

import org.ternlang.common.Cache;
import org.ternlang.common.CompoundIterator;
import org.ternlang.common.HashCache;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.variable.Value;

public class MapState implements ScopeState {
   
   private final Cache<String, Constraint> constraints;
   private final Cache<String, Value> values;
   private final Scope scope;
   
   public MapState() {
      this(null);
   }

   public MapState(Scope scope) {
      this.constraints = new HashCache<String, Constraint>();
      this.values = new HashCache<String, Value>();
      this.scope = scope;
   }
   
   @Override
   public Iterator<String> iterator() {
      Set<String> keys = values.keySet();
      Iterator<String> inner = keys.iterator();
      
      if(scope != null) {
         ScopeState state = scope.getState();
         Iterator<String> outer = state.iterator();
         
         return new CompoundIterator<String>(inner, outer);
      }
      return inner;
   }

   @Override
   public Value getValue(String name) {
      Value value = values.fetch(name);
      
      if(value == null && scope != null) {
         ScopeState state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         return state.getValue(name);
      }
      return value;
   }
   
   @Override
   public void addValue(String name, Value value) {
      Value variable = values.cache(name, value);

      if(variable != null) {
         throw new InternalStateException("Variable '" + name + "' already exists");
      }
   }
   
   @Override
   public Constraint getConstraint(String name) {
      Constraint constraint = constraints.fetch(name);
      
      if(constraint == null && scope != null) {
         ScopeState state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         return state.getConstraint(name);
      }
      return constraint;
   }
   
   @Override
   public void addConstraint(String name, Constraint constraint) {
      Constraint existing = constraints.cache(name, constraint);

      if(existing != null) {
         throw new InternalStateException("Constraint '" + name + "' already exists");
      }
   }
   
   @Override
   public String toString() {
      return String.valueOf(values);
   }
}