package tern.core.scope.instance;

import tern.core.module.Module;
import tern.core.platform.Bridge;
import tern.core.scope.ScopeState;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.type.Type;
import tern.core.variable.Value;

public class SuperInstance implements Instance {

   private final Instance scope;
   private final Module module;
   private final Type type;
   private final Type real;
   
   public SuperInstance(Module module, Instance scope, Type real, Type type) {
      this.scope = scope;
      this.module = module;
      this.type = type;
      this.real = real;
   }

   @Override
   public Instance getStack() {
      return scope.getStack();
   }

   @Override
   public Instance getScope() {
      return scope.getScope();
   }
   
   @Override
   public Instance getSuper(){
      return scope.getSuper();
   }
   
   @Override
   public Bridge getBridge() {
      return scope.getBridge();
   }
   
   @Override
   public Value getThis() {
      return scope.getThis();
   }
   
   @Override
   public Object getProxy() {
      return scope.getProxy();
   }
   
   @Override
   public ScopeState getState() {
      return scope.getState();
   }
   
   @Override
   public ScopeIndex getIndex(){
      return scope.getIndex();
   }
   
   @Override
   public ScopeTable getTable(){
      return scope.getTable();
   }
   
   @Override
   public Type getHandle() {
      return type;
   }

   @Override
   public Type getType() {
      return real;
   }

   @Override
   public Module getModule() {
      return module;
   }
   
   @Override
   public String toString() {
      return scope.toString();
   }
}