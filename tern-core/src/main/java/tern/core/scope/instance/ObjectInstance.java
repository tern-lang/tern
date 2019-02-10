package tern.core.scope.instance;

import tern.core.convert.proxy.ScopeProxy;
import tern.core.module.Module;
import tern.core.platform.Bridge;
import tern.core.scope.ScopeState;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.type.Type;
import tern.core.variable.Value;

public class ObjectInstance implements Instance {

   private final StateAccessor accessor;
   private final ScopeProxy proxy;
   private final Instance base;
   private final Bridge object;
   private final Module module;
   private final Value self;
   private final Type type;
   
   public ObjectInstance(Module module, Instance base, Bridge object, Value self, Type type) {
      this.accessor = new StateAccessor(base);
      this.proxy = new ScopeProxy(this);
      this.object = object;
      this.module = module;
      this.type = type;
      this.base = base;
      this.self = self;
   }
   
   @Override
   public Instance getStack() {
      return new CompoundInstance(module, this, this, type);
   } 
   
   @Override
   public Object getProxy() {
      return proxy.getProxy();
   }
   
   @Override
   public Instance getScope() {
      return this; 
   } 
   
   @Override
   public Instance getSuper(){
      return base;
   }
   
   @Override
   public Bridge getBridge() {
      return object;
   }
   
   @Override
   public Value getThis() {
      return self;
   }
   
   @Override
   public ScopeIndex getIndex(){
      return accessor.index;
   }
   
   @Override
   public ScopeTable getTable(){
      return accessor.table;
   }

   @Override
   public ScopeState getState() {
      return accessor.state;
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
      return type;
   }
   
   @Override
   public String toString(){
      return type.toString();
   }   

   private static class StateAccessor {
   
      private final ScopeIndex index;
      private final ScopeTable table;
      private final ScopeState state;
      
      public StateAccessor(Instance instance) {
         this.index = instance.getIndex();
         this.table = instance.getTable();
         this.state = instance.getState();
      }   
      
      public ScopeIndex getIndex(){
         return index;
      }
      
      public ScopeTable getTable(){
         return table;
      }
   
      public ScopeState getState() {
         return state;
      }
   }
}