package tern.core.scope;

import tern.core.module.Module;
import tern.core.scope.index.ArrayTable;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.scope.index.StackIndex;
import tern.core.type.Type;
import tern.core.variable.Transient;
import tern.core.variable.Value;

public class ModelScope implements Scope {
   
   private final ScopeIndex index;
   private final ScopeTable table;
   private final ScopeState state;
   private final Module module;
   
   public ModelScope(Model model, Module module) {
      this(model, module, null);
   }
   
   public ModelScope(Model model, Module module, Scope scope) {
      this.state = new ModelState(model, scope);
      this.index = new StackIndex(scope);
      this.table = new ArrayTable();
      this.module = module;
   }
   
   @Override
   public Scope getStack() {
      return new CompoundScope(this, this);
   } 
   
   @Override
   public Value getThis() {
      return new Transient(this);
   }
   
   @Override
   public Scope getScope() {
      return this;
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