package tern.core.scope.instance;

import tern.core.module.Module;
import tern.core.platform.Bridge;
import tern.core.scope.MapState;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.scope.index.ArrayTable;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.scope.index.StackIndex;
import tern.core.type.Type;
import tern.core.variable.Reference;
import tern.core.variable.Value;

public class PrimitiveInstance implements Instance {   
   
   private final ScopeIndex index;
   private final ScopeTable table;
   private final ScopeState state;
   private final Module module;
   private final Value self;
   private final Type real;
   private final Type type;
   
   public PrimitiveInstance(Module module, Scope scope, Type real, Type type) {
      this.index = new StackIndex(scope);
      this.state = new MapState(scope);
      this.self = new Reference(this);
      this.table = new ArrayTable();
      this.module = module;
      this.type = type;
      this.real = real;
   }
   
   @Override
   public Instance getStack() {
      return new CompoundInstance(module, this, this, real);
   } 
   
   @Override
   public Instance getScope() {
      return this;
   } 
   
   @Override
   public Instance getSuper(){
      return null;
   }
   
   @Override
   public Bridge getBridge(){
      return null;
   }
   
   @Override
   public Value getThis(){
      return self;
   }
   
   @Override
   public Object getProxy() {
      return null;
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
   public Module getModule() {
      return module;
   }
   
   @Override
   public Type getHandle(){
      return type;
   }
   
   @Override
   public Type getType(){
      return real;
   }

   @Override
   public String toString(){
      return real.toString();
   }
}