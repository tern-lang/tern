package tern.tree.define;

import static tern.core.Reserved.TYPE_CONSTRUCTOR;

import tern.core.Context;
import tern.core.error.ErrorHandler;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionResolver;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;
import tern.core.type.Type;
import tern.tree.ArgumentList;
import tern.tree.construct.ConstructArgumentList;

public class EnumConstructorBinder {

   private final ConstructArgumentList arguments;
   
   public EnumConstructorBinder(ArgumentList arguments) {
      this.arguments = new ConstructArgumentList(arguments);
   }
   
   public Instance bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      FunctionResolver resolver = context.getResolver();
      Object[] array = arguments.create(scope, type);
      FunctionCall call = resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, array);
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, type, TYPE_CONSTRUCTOR, array);
      }
      return (Instance)call.invoke(scope, null, array);
   }
}