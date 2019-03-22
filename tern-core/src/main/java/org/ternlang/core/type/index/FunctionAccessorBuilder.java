package org.ternlang.core.type.index;

import org.ternlang.core.Context;
import org.ternlang.core.function.Accessor;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.TraceAccessor;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.bind.FunctionMatcher;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.type.Type;

public class FunctionAccessorBuilder {

   public FunctionAccessorBuilder() {
      super();
   }

   public Accessor create(Function function, String name) throws Exception {
      Type type = function.getSource();
      Module module = type.getModule();
      Accessor accessor = access(function, name);

      return access(accessor, module);
   }

   private Accessor access(Accessor accessor, Module module) throws Exception {
      Path path = module.getPath();
      Trace trace = Trace.getNative(module, path);
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();

      return new TraceAccessor(interceptor, accessor, module, trace);
   }

   private Accessor access(Function function, String name) throws Exception {
      String identifier = function.getName();
      Type type = function.getSource();
      Module module = type.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      FunctionMatcher matcher = binder.bind(identifier);

      return new FunctionAccessor(matcher, module, identifier);
   }
}
