package tern.tree.define;

import static tern.core.Reserved.TYPE_CONSTRUCTOR;

import tern.core.Context;
import tern.core.constraint.Constraint;
import tern.core.function.bind.FunctionBinder;
import tern.core.function.bind.FunctionMatcher;
import tern.core.function.dispatch.FunctionDispatcher;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

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