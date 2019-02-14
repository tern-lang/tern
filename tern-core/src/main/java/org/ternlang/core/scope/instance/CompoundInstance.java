package org.ternlang.core.scope.instance;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Module;
import org.ternlang.core.platform.Bridge;
import org.ternlang.core.scope.MapState;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.ArrayTable;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.scope.index.StackIndex;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class CompoundInstance implements Instance {
   
   private final ScopeIndex index;
   private final ScopeState state;
   private final ScopeTable table;
   private final Instance instance;
   private final Module module;
   private final Scope outer;
   private final Type type;
   
   public CompoundInstance(Module module, Instance instance, Scope outer, Type type) {
      this.index = new StackIndex(outer);
      this.state = new MapState(outer);
      this.table = new ArrayTable();
      this.instance = instance;
      this.module = module;
      this.outer = outer;
      this.type = type;
   }
   
   @Override
   public Instance getStack() {
      throw new InternalStateException("Stack already created");
   } 
   
   @Override
   public Instance getScope() {
      return instance;
   } 
   
   @Override
   public Instance getSuper() {
      return instance.getSuper();
   }
   
   @Override
   public Bridge getBridge() {
      return instance.getBridge();
   }
   
   @Override
   public Value getThis() {
      return instance.getThis();
   }
   
   @Override
   public Object getProxy() {
      return instance.getProxy();
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
   public ScopeTable getTable(){
      return table;
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
      return outer.toString();
   }
}