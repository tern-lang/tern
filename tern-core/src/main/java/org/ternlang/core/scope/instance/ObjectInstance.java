package org.ternlang.core.scope.instance;

import org.ternlang.core.convert.proxy.ScopeProxy;
import org.ternlang.core.module.Module;
import org.ternlang.core.platform.Bridge;
import org.ternlang.core.scope.ScopeStack;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

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
   public Instance getChild() {
      return new CompoundInstance(module, this, this, type);
   } 
   
   @Override
   public Object getProxy() {
      return proxy.getProxy();
   }
   
   @Override
   public Instance getParent() {
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
   public ScopeStack getStack() {
      return base.getStack();
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
