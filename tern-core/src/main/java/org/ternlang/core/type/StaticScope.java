package org.ternlang.core.type;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.TypeConstraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.CompoundScope;
import org.ternlang.core.scope.MapState;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.ArrayTable;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.scope.index.StackIndex;
import org.ternlang.core.stack.StackTrace;
import org.ternlang.core.variable.Constant;
import org.ternlang.core.variable.Value;

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
   public Module getModule() {
      return type.getModule();
   }
   
   @Override
   public StackTrace getStack() {
      return null;
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