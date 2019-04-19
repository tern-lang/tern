package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.STATIC;

import java.util.concurrent.atomic.AtomicBoolean;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Category;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeState;

public abstract class StaticBlock extends TypeState {

   private final AtomicBoolean allocate;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   
   protected StaticBlock() {
      this.allocate = new AtomicBoolean();
      this.compile = new AtomicBoolean();
      this.define = new AtomicBoolean();
   }
   
   @Override
   public Category define(Scope scope, Type type) throws Exception { 
      if(!define.get()) {
         synchronized(type) { // global lock will not work here
            if(define.compareAndSet(false, true)) { 
               define(scope);
            }
         }
      }
      return STATIC;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception { 
      if(!compile.get()) {
         synchronized(type) { 
            if(compile.compareAndSet(false, true)) { 
               compile(scope);
            }
         }
      }
   }
   
   @Override
   public void allocate(Scope scope, Type type) throws Exception { 
      if(!allocate.get()) {
         synchronized(type) {
            if(allocate.compareAndSet(false, true)) { 
               Scope outer = type.getScope();
               allocate(outer);
            }
         }
      }
   }

   protected void define(Scope scope) throws Exception {}
   protected void compile(Scope scope) throws Exception {}
   protected void allocate(Scope scope) throws Exception {}
}