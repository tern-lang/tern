package org.ternlang.core.function.dispatch;

import java.util.Map;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.proxy.Delegate;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class FunctionDispatcherBuilder {

   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public FunctionDispatcherBuilder(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.handler = handler;
      this.resolver = resolver;
      this.name = name;
   }
   
   public FunctionDispatcher create(Scope scope, Class type) throws Exception  {
      if(Module.class.isAssignableFrom(type)) {
         return new ModuleDispatcher(resolver, handler, name);
      }  
      if(Type.class.isAssignableFrom(type)) {
         return new TypeStaticDispatcher(resolver, handler, name);
      }  
      if(Map.class.isAssignableFrom(type)) {
         return new MapDispatcher(resolver, handler, name);
      }
      if(Function.class.isAssignableFrom(type)) {
         return new ClosureDispatcher(resolver, handler, name);
      }
      if(Delegate.class.isAssignableFrom(type)) { 
         return new DelegateDispatcher(resolver, handler, name);
      }     
      if(Value.class.isAssignableFrom(type)) {
         return new ValueDispatcher(resolver, handler, name);
      }
      if(type.isArray()) {
         return new ArrayDispatcher(resolver, handler, name);
      }
      return new TypeInstanceDispatcher(resolver, handler, name);     
   }
   
   public FunctionDispatcher create(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      Class real = type.getType();
      int modifiers = type.getModifiers();
      
      if(left.isModule()) {
         return new ModuleDispatcher(resolver, handler, name);
      }
      if(left.isClass()) {
         return new TypeStaticDispatcher(resolver, handler, name);
      }
      if(ModifierType.isFunction(modifiers)) {
         return new ClosureDispatcher(resolver, handler, name);
      }
      if(ModifierType.isProxy(modifiers)) {
         return new DelegateDispatcher(resolver, handler, name);
      } 
      if(ModifierType.isArray(modifiers)) {
         return new ArrayDispatcher(resolver, handler, name);
      }
      if(real != null) {
         if(Map.class.isAssignableFrom(real)) {
            return new MapDispatcher(resolver, handler, name);
         }
      }
      return new TypeInstanceDispatcher(resolver, handler, name);      
   }
}
