package tern.compile;

import java.util.concurrent.Executor;

import tern.common.store.Store;
import tern.compile.assemble.ExecutorLinker;
import tern.compile.assemble.OperationEvaluator;
import tern.compile.validate.ExecutableValidator;
import tern.compile.verify.ExecutableVerifier;
import tern.core.Context;
import tern.core.ContextValidator;
import tern.core.resume.ExecutorScheduler;
import tern.core.ExpressionEvaluator;
import tern.core.ResourceManager;
import tern.core.StoreManager;
import tern.core.resume.TaskScheduler;
import tern.core.constraint.transform.ConstraintTransformer;
import tern.core.convert.ConstraintMatcher;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.error.ErrorHandler;
import tern.core.function.bind.FunctionBinder;
import tern.core.function.index.FunctionIndexer;
import tern.core.function.resolve.FunctionResolver;
import tern.core.link.PackageLinker;
import tern.core.module.ModuleRegistry;
import tern.core.platform.PlatformProvider;
import tern.core.stack.ThreadStack;
import tern.core.trace.TraceInterceptor;
import tern.core.type.CacheTypeLoader;
import tern.core.type.TypeExtractor;
import tern.core.type.TypeLoader;

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
      this.interceptor = new TraceInterceptor(verifier, stack);
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
      this.resolver = new FunctionResolver(extractor, stack, indexer);
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