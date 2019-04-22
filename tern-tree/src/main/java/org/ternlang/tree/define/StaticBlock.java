package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.STATIC;

import org.ternlang.common.Guard;
import org.ternlang.common.LockGuard;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Category;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeState;

public abstract class StaticBlock extends TypeState {

   private final Guard<Type> allocate;
   private final Guard<Type> compile;
   private final Guard<Type> define;
   
   protected StaticBlock() {
      this.allocate = new LockGuard<Type>();
      this.compile = new LockGuard<Type>();
      this.define = new LockGuard<Type>();
   }
   
   @Override
   public Category define(Scope scope, Type type) throws Exception { 
      if(define.require(type)) { // global lock would cause deadlock
         try {
            define(scope);
         } finally {
            define.done(type);
         }
      }
      return STATIC;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception { 
      if(compile.require(type)) {
         try {
            compile(scope);
         } finally {
            compile.done(type);
         }
      }
   }
   
   @Override
   public void allocate(Scope scope, Type type) throws Exception { 
      if(allocate.require(type)) {
         try {
            Scope outer = type.getScope();
            allocate(outer); 
         } finally {
            allocate.done(type);
         }
      }
   }

   protected void define(Scope scope) throws Exception {}
   protected void compile(Scope scope) throws Exception {}
   protected void allocate(Scope scope) throws Exception {}
}