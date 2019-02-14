package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.STATIC;

import java.util.concurrent.atomic.AtomicBoolean;

import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.Category;
import org.ternlang.core.type.Type;

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
         Module module = type.getModule();
         Context context = module.getContext();
         
         synchronized(context) { // static lock to force wait
            if(define.compareAndSet(false, true)) { // only do it once
               define(scope);
            }
         }
      }
      return STATIC;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception { 
      if(!compile.get()) {
         Module module = type.getModule();
         Context context = module.getContext();
         
         synchronized(context) { // static lock to force wait
            if(compile.compareAndSet(false, true)) { // only do it once
               compile(scope);
            }
         }
      }
   }
   
   @Override
   public void allocate(Scope scope, Type type) throws Exception { 
      if(!allocate.get()) {
         Module module = type.getModule();
         Context context = module.getContext();
         Scope outer = type.getScope();
         
         synchronized(context) { // static lock to force wait
            if(allocate.compareAndSet(false, true)) { // only do it once
               allocate(outer);
            }
         }
      }
   }

   protected void define(Scope scope) throws Exception {}
   protected void compile(Scope scope) throws Exception {}
   protected void allocate(Scope scope) throws Exception {}
}