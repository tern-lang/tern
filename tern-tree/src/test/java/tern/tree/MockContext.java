package tern.tree;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
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
import tern.core.link.NoPackage;
import tern.core.link.Package;
import tern.core.link.PackageLinker;
import tern.core.module.ModuleRegistry;
import tern.core.module.Path;
import tern.core.platform.PlatformProvider;
import tern.core.stack.ThreadStack;
import tern.core.trace.TraceInterceptor;
import tern.core.type.CacheTypeLoader;
import tern.core.type.TypeExtractor;
import tern.core.type.TypeLoader;

public class MockContext implements Context {
   
   private final ConstraintTransformer transformer;
   private final ConstraintMatcher matcher;
   private final ResourceManager manager;
   private final ModuleRegistry registry;
   private final TaskScheduler scheduler;
   private final TypeLoader loader;
   private final TypeExtractor extractor;
   private final ThreadStack stack;
   private final ProxyWrapper wrapper;
   private final FunctionResolver resolver;
   private final FunctionIndexer indexer;
   private final PackageLinker linker;
   private final ErrorHandler handler;
   private final FunctionBinder table;
   private final Store store;
   
   public MockContext(){
      this.linker = new TestLinker();
      this.store = new ClassPathStore();
      this.stack = new ThreadStack();
      this.wrapper = new ProxyWrapper(this);
      this.manager = new StoreManager(store);
      this.registry = new ModuleRegistry(this, null);
      this.loader = new CacheTypeLoader(linker, registry, manager, wrapper, stack);
      this.extractor = new TypeExtractor(loader);
      this.transformer = new ConstraintTransformer(extractor);
      this.indexer = new FunctionIndexer(extractor, stack);
      this.resolver = new FunctionResolver(extractor, stack, indexer);
      this.matcher = new ConstraintMatcher(loader, wrapper);
      this.handler = new ErrorHandler(extractor, stack);
      this.table = new FunctionBinder(resolver, handler);
      this.scheduler = new ExecutorScheduler(handler, null);
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
   public FunctionBinder getBinder() {
      return table;
   }

   @Override
   public ErrorHandler getHandler() {
      return handler;
   }

   @Override
   public TypeExtractor getExtractor() {
      return extractor;
   }

   @Override
   public ResourceManager getManager() {
      return manager;
   }

   @Override
   public ModuleRegistry getRegistry() {
      return registry;
   }

   @Override
   public ConstraintMatcher getMatcher() {
      return matcher;
   }

   @Override
   public ContextValidator getValidator() {
      return null;
   }

   @Override
   public TraceInterceptor getInterceptor() {
      return null;
   }

   @Override
   public ExpressionEvaluator getEvaluator() {
      return null;
   }
   
   @Override
   public PlatformProvider getProvider() {
      return null;
   }

   @Override
   public FunctionResolver getResolver() {
      return resolver;
   }

   @Override
   public PackageLinker getLinker() {
      return linker;
   }

   @Override
   public ProxyWrapper getWrapper() {
      return wrapper;
   }

   @Override
   public ConstraintTransformer getTransformer() {
      return transformer;
   }
   
   @Override
   public TypeLoader getLoader() {
      return loader;
   }
   
   private static class TestLinker implements PackageLinker {
      
      @Override
      public Package link(Path name, String source) throws Exception {
         return new NoPackage();
      }
      
      @Override
      public Package link(Path name, String source, String grammar) throws Exception {
         return new NoPackage();
      }
   }

}