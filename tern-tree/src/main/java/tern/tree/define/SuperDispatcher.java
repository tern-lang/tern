package tern.tree.define;

import static tern.core.ModifierType.CONSTANT;

import tern.core.Context;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.Connection;
import tern.core.function.Invocation;
import tern.core.function.dispatch.FunctionDispatcher;
import tern.core.module.Module;
import tern.core.platform.Platform;
import tern.core.platform.PlatformProvider;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

public class SuperDispatcher implements FunctionDispatcher {

   private final Type type;
   
   public SuperDispatcher(Type type) {
      this.type = type;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint object, Type... list) throws Exception {
      return Constraint.getConstraint(type, CONSTANT.mask);
   }
   
   @Override
   public Connection connect(Scope scope, Value value, Object... list) throws Exception {
      Type real = (Type)list[0];
      Module module = scope.getModule();
      Context context = module.getContext();
      Class base = type.getType();
      
      if(base == null) {
         throw new InternalStateException("Base type of '" + type + "' is null");
      }
      Object[] copy = new Object[list.length - 1];
      
      if(copy.length > 0) {
         System.arraycopy(list, 1, copy, 0, copy.length);
      }
      PlatformProvider provider = context.getProvider();
      Platform platform = provider.create();
      Invocation invocation = platform.createSuperConstructor(real, type);
      Object instance = invocation.invoke(scope, real, copy);
      
      return new SuperConnection(instance);
   }
   
   private static class SuperConnection implements Connection {
      
      private final Object instance;
      
      public SuperConnection(Object instance) {
         this.instance = instance;
      }

      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return false;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         return instance;
      }
   }
}