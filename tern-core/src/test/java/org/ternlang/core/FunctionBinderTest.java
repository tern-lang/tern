package org.ternlang.core;

import java.util.HashMap;
import java.util.Map;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.constraint.transform.ConstraintTransformer;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.index.FunctionIndexer;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.link.NoPackage;
import org.ternlang.core.link.Package;
import org.ternlang.core.link.PackageLinker;
import org.ternlang.core.module.ContextModule;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.module.Path;
import org.ternlang.core.platform.PlatformProvider;
import org.ternlang.core.resume.ExecutorScheduler;
import org.ternlang.core.resume.TaskScheduler;
import org.ternlang.core.scope.EmptyModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.ModelScope;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.type.CacheTypeLoader;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;

import junit.framework.TestCase;

public class FunctionBinderTest extends TestCase {
   
   private static final int ITERATIONS = 1000000;
   
   public void testBinder() throws Exception {
      Map<String, Object> map = new HashMap<String, Object>();
      Context context = new TestContext();
      Model model = new EmptyModel();
      Path path = new Path(Reserved.DEFAULT_RESOURCE);
      Module module = new ContextModule(context, null, path, Reserved.DEFAULT_MODULE, "");
      Scope scope = new ModelScope(model, module);
      
      context.getResolver().resolveInstance(scope, map, "put", "x", 11).invoke(scope, map, "x", 11);
      context.getResolver().resolveInstance(scope, map, "put", "y", 21).invoke(scope, map, "y", 21);
      context.getResolver().resolveInstance(scope, map, "put", "z", 441).invoke(scope, map, "z", 441);
      
      assertEquals(map.get("x"), 11);
      assertEquals(map.get("y"), 21);
      assertEquals(map.get("z"), 441);
      
      context.getResolver().resolveInstance(scope, map, "put", "x", 22).invoke(scope, map, "x", 22);
      context.getResolver().resolveInstance(scope, map, "remove", "y").invoke(scope, map, "y");
      context.getResolver().resolveInstance(scope, map, "put", "z", "x").invoke(scope, map, "z", "x");
      
      assertEquals(map.get("x"), 22);
      assertEquals(map.get("y"), null);
      assertEquals(map.get("z"), "x");
      
      assertEquals(context.getResolver().resolveInstance(scope, map, "put", "x", 44).invoke(scope, map, "x", 44), 22);
      assertEquals(context.getResolver().resolveInstance(scope, map, "put", "y", true).invoke(scope, map, "y", true), null);
      assertEquals(context.getResolver().resolveInstance(scope, map, "put", "z", "x").invoke(scope, map, "z", "x"), "x");
      
      long start = System.currentTimeMillis();
      
      for(int i = 0; i < ITERATIONS; i++) {
         context.getResolver().resolveInstance(scope, map, "put", "x", 44);
      }
      long finish = System.currentTimeMillis();
      double milliseconds = finish - start;
      double seconds = milliseconds / 1000.0;
      
      System.err.println("Time taken: " + seconds + " seconds, Invocations per second: " +(ITERATIONS/seconds));
   }
   
   
   
   public void testBinderPerformance() throws Exception {
//
//      PackageLinker linker = new PackageLinker() {
//         
//         @Override
//         public Package link(String name, String source) throws Exception {
//            return null;
//         }
//         @Override
//         public Package link(String name, String source, String grammar) throws Exception {
//            return null;
//         }
//      };
//      Store store = new ClassPathStore();
//      ResourceManager manager = new StoreManager(store);
//      PackageManager resolver = new PackageManager(linker, manager);
//      ModuleBuilder builder = new ModuleBuilder(null);
//      TypeLoader loader = new TypeLoader(resolver, builder);
//      ConstraintMatcher matcher = new ConstraintMatcher(loader);
//      FunctionBinder binder = new FunctionBinder(matcher, loader);
//      Type type = loader.loadType(Map.class);
//      long start = System.currentTimeMillis();
//      
//      for(int i = 0; i< 1000000; i++) {
//         Map<String, Object> map = new HashMap<String, Object>();
//         
//         binder.bind(null, map, "put", "x", 11).call();
//         binder.bind(null, map, "put", "y", 21).call();
//         binder.bind(null, map, "put", "z", 441).call();
//         
//         assertEquals(map.get("x"), 11);
//         assertEquals(map.get("y"), 21);
//         assertEquals(map.get("z"), 441);
//      }
//      long finish = System.currentTimeMillis();
//      long duration = finish - start;
//      
//      System.err.println("Duration " + duration);
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
   
   private static class TestContext implements Context {
      
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
      
      public TestContext(){
         this.linker = new TestLinker();
         this.store = new ClassPathStore();
         this.stack = new ThreadStack();
         this.wrapper = new ProxyWrapper(this);
         this.manager = new StoreManager(store);
         this.registry = new ModuleRegistry(this, null);
         this.loader = new CacheTypeLoader(linker, registry, manager, wrapper);
         this.extractor = new TypeExtractor(loader);
         this.transformer = new ConstraintTransformer(extractor);
         this.indexer = new FunctionIndexer(extractor);
         this.resolver = new FunctionResolver(extractor, wrapper, indexer);
         this.matcher = new ConstraintMatcher(loader, wrapper);
         this.handler = new ErrorHandler(extractor, stack);
         this.table = new FunctionBinder(resolver, handler);
         this.scheduler = new ExecutorScheduler(handler,null);
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
      public TypeLoader getLoader() {
         return loader;
      }

      @Override
      public ConstraintTransformer getTransformer() {
         return transformer;
      }
   }
}
