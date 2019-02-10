package tern.core.function.dispatch;

import static tern.core.constraint.Constraint.NONE;

import java.util.Map;

import tern.core.Context;
import tern.core.constraint.Constraint;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.error.ErrorHandler;
import tern.core.function.Connection;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionConnection;
import tern.core.function.resolve.FunctionResolver;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

public class MapDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;      
   
   public MapDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type type = constraint.getType(scope);
      FunctionCall local = resolver.resolveInstance(scope, type, name, arguments);
      
      if(local != null) {
         return local.check(scope, constraint, arguments);
      }
      return NONE;      
   }
   
   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception {
      Map map = value.getValue();
      FunctionCall call = bind(scope, map, arguments);
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, map, name, arguments);
      }
      return new FunctionConnection(call);
   }
   
   private FunctionCall bind(Scope scope, Map map, Object... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = resolver.resolveInstance(scope, map, name, arguments);
      
      if(local == null) {
         Object value = map.get(name);
         
         if(value != null) {
            Context context = module.getContext();
            ProxyWrapper wrapper = context.getWrapper();
            Object function = wrapper.fromProxy(value);
            Value reference = Value.getTransient(function);
            
            return resolver.resolveValue(reference, arguments);
         }
      }
      return local;
   }
}