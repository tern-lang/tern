package org.ternlang.core.convert.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;

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