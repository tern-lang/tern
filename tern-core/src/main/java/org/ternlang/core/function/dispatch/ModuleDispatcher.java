package org.ternlang.core.function.dispatch;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.ArgumentListCompiler;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class ModuleDispatcher implements FunctionDispatcher {
   
   private final ArgumentListCompiler compiler;
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public ModuleDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.compiler = new ArgumentListCompiler();
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Constraint... arguments) throws Exception {
      Type type = constraint.getType(scope);
      Module module = type.getModule();
      Type[] types = compiler.compile(scope, arguments);
      FunctionCall call = bind(scope, module, types);
      
      if(call == null) {
         handler.failCompileInvocation(scope, type, name, types);
      }
      return call.check(scope, constraint, arguments);
   }

   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception {
      Module module = value.getValue();
      Connection call = bind(scope, module, arguments);
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, module, name, arguments);
      }
      return call;     
   }
   
   private FunctionCall bind(Scope scope, Module module, Type... arguments) throws Exception {
      Scope inner = module.getScope();
      FunctionCall call = resolver.resolveModule(inner, module, name, arguments);
      
      if(call == null) {
         return resolver.resolveInstance(inner, (Object)module, name, arguments);
      }
      return call;
   }
   
   private Connection bind(Scope scope, Module module, Object... arguments) throws Exception {
      Scope inner = module.getScope();
      FunctionCall call = resolver.resolveModule(inner, module, name, arguments);
      
      if(call == null) {
         call = resolver.resolveInstance(inner, (Object)module, name, arguments);
      }
      if(call != null) {
         return new ModuleConnection(call, module);
      }
      return null;
   }
   
   private static class ModuleConnection implements Connection<Value> {

      private final FunctionCall call;
      private final Module module;
      
      public ModuleConnection(FunctionCall call, Module module) {
         this.module = module;
         this.call = call;
      }

      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return call.match(scope, object, arguments);
      }
      
      @Override
      public Object invoke(Scope scope, Value value, Object... arguments) throws Exception {
         Scope inner = module.getScope();
         return call.invoke(inner, module, arguments);
      }
   }
}