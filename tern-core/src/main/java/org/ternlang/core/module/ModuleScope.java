package org.ternlang.core.module;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.ModuleConstraint;
import org.ternlang.core.scope.CompoundScope;
import org.ternlang.core.scope.MapState;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.ArrayTable;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.scope.index.StackIndex;
import org.ternlang.core.stack.StackTrace;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Constant;
import org.ternlang.core.variable.Value;

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
   public Scope getChild() {
      return new CompoundScope(this, this);
   } 
   
   @Override
   public Value getThis() {
      return self;
   }
   
   @Override
   public Scope getParent() {
      return this;
   }
   
   @Override
   public StackTrace getStack() {
      return null;
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