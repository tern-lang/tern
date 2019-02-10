package tern.core;

import tern.core.constraint.transform.ConstraintTransformer;
import tern.core.convert.ConstraintMatcher;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.error.ErrorHandler;
import tern.core.function.bind.FunctionBinder;
import tern.core.function.resolve.FunctionResolver;
import tern.core.link.PackageLinker;
import tern.core.module.ModuleRegistry;
import tern.core.platform.PlatformProvider;
import tern.core.resume.TaskScheduler;
import tern.core.stack.ThreadStack;
import tern.core.trace.TraceInterceptor;
import tern.core.type.TypeExtractor;
import tern.core.type.TypeLoader;

public interface Context extends Any {
   ThreadStack getStack();
   ErrorHandler getHandler();
   TaskScheduler getScheduler();
   TypeExtractor getExtractor();
   ResourceManager getManager();
   ModuleRegistry getRegistry();
   ConstraintMatcher getMatcher();
   ContextValidator getValidator();
   TraceInterceptor getInterceptor();
   ExpressionEvaluator getEvaluator();
   ConstraintTransformer getTransformer();
   FunctionResolver getResolver();
   PlatformProvider getProvider();
   PackageLinker getLinker();
   ProxyWrapper getWrapper();
   FunctionBinder getBinder();
   TypeLoader getLoader();

   
}