package org.ternlang.core.scope;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.index.ArrayTable;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.scope.index.StackIndex;
import org.ternlang.core.stack.StackFrame;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class CompoundScope implements Scope {
   
   private final StackFrame frame;
   private final ScopeIndex index;
   private final ScopeTable table;
   private final ScopeState state;
   private final Scope outer;
   
   public CompoundScope(Scope inner, Scope outer) {
      this.frame = new StackFrame(outer);
      this.index = new StackIndex(inner);
      this.state = new MapState(inner);
      this.table = new ArrayTable();
      this.outer = outer;
   } 
  
   @Override
   public Scope getChild() {
      throw new InternalStateException("Stack already created");
   }  
   
   @Override
   public Scope getParent() {
      return outer;
   }
   
   @Override
   public Value getThis() {
      return outer.getThis();
   }
   
   @Override
   public Type getHandle() {
      return outer.getType();
   }
   
   @Override
   public Type getType() {
      return outer.getType();
   }
  
   @Override
   public Module getModule() {
      return outer.getModule();
   } 
   
   @Override
   public ScopeStack getStack() {
      return frame.getTrace();
   }
   
   @Override
   public ScopeIndex getIndex(){
      return index;
   }
   
   @Override
   public ScopeTable getTable(){
      return table;
   }
   
   @Override
   public ScopeState getState() {
      return state;
   }
   
   @Override
   public String toString() {
      return String.valueOf(state);
   }
}