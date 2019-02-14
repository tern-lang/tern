package org.ternlang.core.convert.proxy;

import java.lang.reflect.Proxy;

import org.ternlang.core.Any;
import org.ternlang.core.Context;
import org.ternlang.core.ContextClassLoader;
import org.ternlang.core.convert.InterfaceCollector;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.trace.TraceInterceptor;

public class ProxyFactory {

   private final InterfaceCollector collector;
   private final ProxyWrapper wrapper;
   private final ClassLoader loader;
   private final Context context;
   
   public ProxyFactory(ProxyWrapper wrapper, Context context) {
      this.loader = new ContextClassLoader(Any.class);
      this.collector = new InterfaceCollector();
      this.wrapper = wrapper;
      this.context = context;
   }
   
   public Object create(Instance instance) {
      Class[] interfaces = collector.collect(instance);
      FunctionResolver resolver = context.getResolver();
      TraceInterceptor interceptor = context.getInterceptor();
      ErrorHandler handler = context.getHandler();

      if(interfaces.length == 0) {
         throw new InternalStateException("No interfaces found for instance");
      }
      if(interfaces.length == 1) {
         return new AnyProxy(wrapper, resolver, instance);
      }
      ScopeProxyHandler delegate = new ScopeProxyHandler(wrapper, resolver, instance);
      TraceProxyHandler tracer = new TraceProxyHandler(delegate, interceptor, handler, instance);

      return Proxy.newProxyInstance(loader, interfaces, tracer);
   }
   
   public Object create(Function function, Class require) {
      Class[] interfaces = collector.collect(Delegate.class, require);
      FunctionResolver resolver = context.getResolver();

      if(interfaces.length == 0) {
         throw new InternalStateException("No interfaces found for function");
      }
      FunctionProxyHandler handler = new FunctionProxyHandler(wrapper, resolver, function);

      return Proxy.newProxyInstance(loader, interfaces, handler);
   }
}