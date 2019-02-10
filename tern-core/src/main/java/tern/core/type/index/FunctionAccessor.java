package tern.core.type.index;

import tern.core.error.InternalStateException;
import tern.core.function.Accessor;
import tern.core.function.Invocation;
import tern.core.function.bind.FunctionMatcher;
import tern.core.function.dispatch.FunctionDispatcher;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class FunctionAccessor implements Accessor {

   private final FunctionMatcher matcher;
   private final Module module;
   private final String name;
   
   public FunctionAccessor(FunctionMatcher matcher, Module module, String name) {
      this.matcher = matcher;
      this.module = module;
      this.name = name;
   }
   
   @Override
   public Object getValue(Object source) {
      Scope scope = module.getScope();
      Value value = Value.getTransient(source);
      
      try {
         FunctionDispatcher dispatcher = matcher.match(scope, value);
         Invocation invocation = dispatcher.connect(scope, value);
         
         if(Scope.class.isInstance(source)) {
            return invocation.invoke((Scope)source, value);
         }
         return invocation.invoke(scope, value);
      } catch(Exception e) {
         throw new InternalStateException("Error occurred invoking '" + name + "()'", e);
      }
   }

   @Override
   public void setValue(Object source, Object value) {
      throw new InternalStateException("Illegal modification of '" + name + "()'");
   }

}