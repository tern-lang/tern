package org.ternlang.core;

import org.ternlang.core.constraint.transform.ConstraintTransformer;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.link.PackageLinker;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.platform.PlatformProvider;
import org.ternlang.core.resume.TaskScheduler;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;

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