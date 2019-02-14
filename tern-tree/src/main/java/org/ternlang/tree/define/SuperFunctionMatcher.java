package org.ternlang.tree.define;

import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.bind.FunctionMatcher;
import org.ternlang.core.function.dispatch.FunctionDispatcher;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class SuperFunctionMatcher {

   private FunctionDispatcher dispatcher;
   private Type type;
   
   public SuperFunctionMatcher(Type type) {
      this.type = type;
   }
   
   public FunctionDispatcher match(Scope scope, Constraint left) throws Exception {
      return resolve(scope, left);
   }

   public FunctionDispatcher match(Scope scope, Value left) throws Exception {
      if(dispatcher == null) {
         dispatcher = resolve(scope, left);
      }
      return dispatcher;
   }
   
   private FunctionDispatcher resolve(Scope scope, Constraint left) throws Exception {
      Class base = type.getType();
      
      if(base == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();     
         FunctionMatcher matcher = binder.bind(TYPE_CONSTRUCTOR);
         
         return matcher.match(scope, left);
      }
      return new SuperDispatcher(type);
   }
   
   private FunctionDispatcher resolve(Scope scope, Value left) throws Exception {
      Class base = type.getType();
      
      if(base == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();
         FunctionMatcher matcher = binder.bind(TYPE_CONSTRUCTOR);
         
         return matcher.match(scope, left);
      }
      return new SuperDispatcher(type);
   }
   
}