package org.ternlang.compile;

import java.util.concurrent.Executor;

import org.ternlang.common.store.Store;
import org.ternlang.compile.assemble.ExecutorLinker;
import org.ternlang.compile.assemble.OperationEvaluator;
import org.ternlang.compile.validate.ExecutableValidator;
import org.ternlang.compile.verify.ExecutableVerifier;
import org.ternlang.core.Context;
import org.ternlang.core.ContextValidator;
import org.ternlang.core.ExpressionEvaluator;
import org.ternlang.core.ResourceManager;
import org.ternlang.core.StoreManager;
import org.ternlang.core.constraint.transform.ConstraintTransformer;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.index.FunctionIndexer;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.link.PackageLinker;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.platform.PlatformProvider;
import org.ternlang.core.resume.ExecutorScheduler;
import org.ternlang.core.resume.TaskScheduler;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.type.CacheTypeLoader;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;

public class StoreContext implements Context {

   private final ExecutableValidator validator;
   private final ConstraintTransformer transformer;
   private final ExpressionEvaluator evaluator;
   private final TraceInterceptor interceptor;
   private final PlatformProvider provider;
   private final ExecutableVerifier verifier;
   private final ConstraintMatcher matcher;
   private final ResourceManager manager;
   private final FunctionIndexer indexer;
   private final FunctionResolver resolver;
   private final TaskScheduler scheduler;
   private final TypeExtractor extractor;
   private final ModuleRegistry registry;
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final ProxyWrapper wrapper;
   private final PackageLinker linker;
   private final ThreadStack stack;
   private final TypeLoader loader; 
   
   public StoreContext(Store store){
      this(store, null);
   }
   
   public StoreContext(Store store, Executor executor){
      this.stack = new ThreadStack();
      this.wrapper = new ProxyWrapper(this);
      this.verifier = new ExecutableVerifier();
      this.interceptor = new TraceInterceptor(verifier);
      this.manager = new StoreManager(store);
      this.registry = new ModuleRegistry(this, executor);
      this.linker = new ExecutorLinker(this, executor);      
      this.loader = new CacheTypeLoader(linker, registry, manager, wrapper, stack);
      this.matcher = new ConstraintMatcher(loader, wrapper);
      this.extractor = new TypeExtractor(loader);
      this.transformer = new ConstraintTransformer(extractor);
      this.handler = new ErrorHandler(extractor, stack);
      this.indexer = new FunctionIndexer(extractor, stack);
      this.validator = new ExecutableValidator(matcher, transformer, extractor, indexer, verifier);
      this.resolver = new FunctionResolver(extractor, wrapper, indexer, stack);
      this.evaluator = new OperationEvaluator(this, verifier, executor);
      this.provider = new PlatformProvider(extractor, wrapper, stack);
      this.binder = new FunctionBinder(resolver, handler);
      this.scheduler = new ExecutorScheduler(handler, executor);
   }
   
   @Override
   public TypeExtractor getExtractor(){
      return extractor;
   }
   
   @Override
   public ThreadStack getStack() {
      return stack;
   }

   @Override
   public TaskScheduler getScheduler() {
      return scheduler;
   }

   @Override
   public ProxyWrapper getWrapper() {
      return wrapper;
   }
   
   @Override
   public ErrorHandler getHandler() {
      return handler;
   }
   
   @Override
   public ContextValidator getValidator() {
      return validator;
   }
   
   @Override
   public ConstraintMatcher getMatcher() {
      return matcher;
   }
   
   @Override
   public ConstraintTransformer getTransformer() {
      return transformer;
   }
   
   @Override
   public TraceInterceptor getInterceptor() {
      return interceptor;
   }
   
   @Override
   public ResourceManager getManager() {
      return manager;
   }
   
   @Override
   public ExpressionEvaluator getEvaluator() {
      return evaluator;
   }

   @Override
   public ModuleRegistry getRegistry() {
      return registry;
   }  
   
   @Override
   public PlatformProvider getProvider() {
      return provider;
   }
   
   @Override
   public FunctionResolver getResolver() {
      return resolver;
   }

   @Override
   public FunctionBinder getBinder() {
      return binder;
   }

   @Override
   public TypeLoader getLoader() {
      return loader;
   }

   @Override
   public PackageLinker getLinker() {
      return linker;
   }
}