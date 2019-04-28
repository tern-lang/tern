package org.ternlang.core.scope;

import org.ternlang.core.module.Module;
import org.ternlang.core.scope.index.ArrayTable;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.scope.index.StackIndex;
import org.ternlang.core.stack.StackFrame;
import org.ternlang.core.stack.StackTrace;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Transient;
import org.ternlang.core.variable.Value;

public class ModelScope implements Scope {
   
   private final StackFrame frame;
   private final ScopeIndex index;
   private final ScopeTable table;
   private final ScopeState state;
   private final Module module;
   
   public ModelScope(Model model, Module module) {
      this(model, module, null);
   }
   
   public ModelScope(Model model, Module module, Scope scope) {
      this.state = new ModelState(model, scope);
      this.frame = new StackFrame(this, true);
      this.index = new StackIndex(scope);
      this.table = new ArrayTable();
      this.module = module;
   }
   
   @Override
   public Scope getChild() {
      return new CompoundScope(this, this);
   } 
   
   @Override
   public Value getThis() {
      return new Transient(this);
   }
   
   @Override
   public Scope getParent() {
      return this;
   } 
   
   @Override
   public StackTrace getStack() {
      return frame.getTrace();
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