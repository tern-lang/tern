package org.ternlang.tree.define;

import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import org.ternlang.core.Context;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.type.Type;
import org.ternlang.tree.ArgumentList;
import org.ternlang.tree.construct.ConstructArgumentList;

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