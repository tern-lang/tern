package org.ternlang.core.function;

import junit.framework.TestCase;
import org.ternlang.common.functional.Spread;
import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.Context;
import org.ternlang.core.ContextValidator;
import org.ternlang.core.ExpressionEvaluator;
import org.ternlang.core.Reserved;
import org.ternlang.core.ResourceManager;
import org.ternlang.core.StoreManager;
import org.ternlang.core.constraint.transform.ConstraintTransformer;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.index.ClosureAdapter;
import org.ternlang.core.function.index.FunctionIndexer;
import org.ternlang.core.function.resolve.FunctionCall;
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

import java.util.Arrays;

public class SeriesAsVarArgsTest extends TestCase {

   private static class Foo {
      public final Spread<String> values;

      public Foo(Spread<String> values) {
         this.values = values;
      }
   }

   private static class Blah {
      public final String[] values;

      public Blah(String... values) {
         this.values = values;
      }
   }

   public void testVarArgsAsSeries() throws Exception {
      Context context = new TestContext();
      Model model = new EmptyModel();
      Path path = new Path(Reserved.DEFAULT_RESOURCE);
      Module module = new ContextModule(context, null, path, Reserved.DEFAULT_MODULE, "");
      Scope scope = new ModelScope(model, module);
      FunctionCall call = context.getResolver().resolveStatic(
           scope,
           context.getLoader().loadType(Foo.class),
           Reserved.TYPE_CONSTRUCTOR,
           context.getLoader().loadType(String.class),
           context.getLoader().loadType(String.class),
           context.getLoader().loadType(String.class));

      Foo foo = (Foo) call.invoke(scope, null, new String[]{"a", "b", "c"});
      assertEquals(foo.values.toList(), Arrays.asList("a", "b", "c"));
   }

   public void testVarArgs() throws Exception {
      Context context = new TestContext();
      Model model = new EmptyModel();
      Path path = new Path(Reserved.DEFAULT_RESOURCE);
      Module module = new ContextModule(context, null, path, Reserved.DEFAULT_MODULE, "");
      Scope scope = new ModelScope(model, module);
      FunctionCall call = context.getResolver().resolveStatic(
           scope,
           context.getLoader().loadType(Blah.class),
           Reserved.TYPE_CONSTRUCTOR,
           context.getLoader().loadType(String.class),
           context.getLoader().loadType(String.class),
           context.getLoader().loadType(String.class));

      Blah blah = (Blah) call.invoke(scope, null, new String[]{"a", "b", "c"});
      assertEquals(Arrays.asList(blah.values), Arrays.asList("a", "b", "c"));
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
      private final ClosureAdapter adapter;
      private final PackageLinker linker;
      private final ErrorHandler handler;
      private final FunctionBinder table;
      private final Store store;

      public TestContext() {
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
         this.matcher = new ConstraintMatcher(loader, wrapper);
         this.adapter = new ClosureAdapter(matcher, extractor, loader, stack);
         this.resolver = new FunctionResolver(extractor, wrapper, indexer, adapter, stack);
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
      public TypeLoader getLoader() {
         return loader;
      }

      @Override
      public ConstraintTransformer getTransformer() {
         return transformer;
      }
   }
}
