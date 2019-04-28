package org.ternlang.core.scope.instance;

import org.ternlang.core.module.Module;
import org.ternlang.core.platform.Bridge;
import org.ternlang.core.scope.ScopeStack;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

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
   public Instance getChild() {
      return scope.getChild();
   }

   @Override
   public Instance getParent() {
      return scope.getParent();
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
   public ScopeStack getStack() {
      return scope.getStack();
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