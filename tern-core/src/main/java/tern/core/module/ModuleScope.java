package tern.core.module;

import tern.core.constraint.Constraint;
import tern.core.constraint.ModuleConstraint;
import tern.core.scope.CompoundScope;
import tern.core.scope.MapState;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.scope.index.ArrayTable;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.scope.index.StackIndex;
import tern.core.type.Type;
import tern.core.variable.Constant;
import tern.core.variable.Value;

public class ModuleScope implements Scope {
   
   private final Constraint constraint;
   private final ScopeIndex index;
   private final ScopeTable table;
   private final ScopeState state;
   private final Module module;
   private final Value self;
   
   public ModuleScope(Module module) {
      this.constraint = new ModuleConstraint(module);
      this.self = new Constant(this, constraint);
      this.state = new MapState(null);
      this.index = new StackIndex(this);
      this.table = new ArrayTable();
      this.module = module;
   }
   
   @Override
   public Scope getStack() {
      return new CompoundScope(this, this);
   } 
   
   @Override
   public Value getThis() {
      return self;
   }
   
   @Override
   public Scope getScope() {
      return this;
   }

   @Override
   public ScopeState getState() {
      return state;
   }
   
   @Override
   public ScopeIndex getIndex(){
      return index;
   }
   
   @Override
   public ScopeTable getTable() {
      return table;
   }
   
   @Override
   public Type getHandle() {
      return null;
   }
   
   @Override
   public Type getType() {
      return null;
   }  

   @Override
   public Module getModule() {
      return module;
   }
   
   @Override
   public String toString() {
      return String.valueOf(state);
   }
}