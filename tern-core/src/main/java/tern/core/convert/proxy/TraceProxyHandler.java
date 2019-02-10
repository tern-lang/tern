package tern.core.convert.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;

public class TraceProxyHandler implements ProxyHandler {

   private final TraceInterceptor interceptor;
   private final InvocationHandler delegate;
   private final ErrorHandler handler;
   private final Scope scope;

   public TraceProxyHandler(InvocationHandler delegate, TraceInterceptor interceptor, ErrorHandler handler, Scope scope) {
      this.interceptor = interceptor;
      this.delegate = delegate;
      this.handler = handler;
      this.scope = scope;
   }

   @Override
   public Object invoke(Object proxy, Method method, Object[] list) throws Throwable {
      Module module = scope.getModule();
      Path path = module.getPath();
      Trace trace = Trace.getNative(module, path);
      
      try {
         interceptor.traceBefore(scope, trace);
         return delegate.invoke(proxy, method, list); 
      } catch(Exception cause) {
         return handler.failInternalError(scope, cause);
      } finally {
         interceptor.traceAfter(scope, trace);
      }
   }
   
   @Override
   public Scope extract() {
      return scope;
   }  
}