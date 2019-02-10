package tern.core.type;

import tern.core.constraint.Constraint;
import tern.core.constraint.TypeConstraint;
import tern.core.module.Module;
import tern.core.scope.CompoundScope;
import tern.core.scope.MapState;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.scope.index.ArrayTable;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.scope.index.StackIndex;
import tern.core.variable.Constant;
import tern.core.variable.Value;

public class StaticScope implements Scope {
   
   private final Constraint constraint;
   private final ScopeIndex index;
   private final ScopeTable table;
   private final ScopeState state;
   private final Value self;
   private final Type type;
   
   public StaticScope(Type type) {
      this.constraint = new TypeConstraint(type);
      this.self = new Constant(this, constraint);
      this.index = new StackIndex(this);
      this.table = new ArrayTable();
      this.state = new MapState();
      this.type = type;
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
   public Module getModule() {
      return type.getModule();
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
   public ScopeState getState() {
      return state;
   }
   
   @Override
   public Type getHandle() {
      return type;
   }
   
   @Override
   public Type getType(){
      return type;
   }  
   
   @Override
   public String toString() {
      return String.valueOf(state);
   }

}