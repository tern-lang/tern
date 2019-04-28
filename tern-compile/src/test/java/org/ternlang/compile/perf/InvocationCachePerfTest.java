package org.ternlang.compile.perf;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.compile.StoreContext;
import org.ternlang.compile.StringCompiler;
import org.ternlang.core.Context;
import org.ternlang.core.Reserved;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationCache;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.bind.FunctionMatcher;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.MapModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypeExtractor;

import junit.framework.TestCase;

public class InvocationCachePerfTest extends TestCase {
   
   private static final int ITERATIONS = 10000000; // 10 million
   
   private static final String SOURCE_1 =
   "func fun(n) {\n"+
   "   return n;\n"+
   "}\n";
   
   private static final String SOURCE_2 =
   "func fun(n) {\n"+
   "   return n;\n"+
   "}\n"+
   "func fun(a, b) {\n"+
   "   return a;\n"+
   "}\n";   
   
   private static final String SOURCE_3 =
   "func fun(n) {\n"+
   "   return n;\n"+
   "}\n"+
   "func fun(a, b) {\n"+
   "   return a;\n"+
   "}\n"+
   "func fun(a, b, c) {\n"+
   "   return a;\n"+
   "}\n";    
 
   public void testInvocationCache() throws Exception {
      resolve("fun", SOURCE_1).timeInvocation(ITERATIONS, 20);
      resolve("fun", SOURCE_2).timeInvocation(ITERATIONS, 20, 1);
      resolve("fun", SOURCE_3).timeInvocation(ITERATIONS, 20, 32, 3);
      resolve("fun", SOURCE_2).timeInvocation(ITERATIONS, 20, 1);
   }
   
   private static InvocationContext resolve(String function, String source) throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      StringCompiler compiler = new StringCompiler(context);
      compiler.compile(source).execute(model);
      FunctionBinder binder = context.getBinder();
      FunctionMatcher matcher = binder.bind(function);
      TypeExtractor extractor = context.getExtractor();
      InvocationCache cache = new InvocationCache(matcher, extractor);
      Module module = context.getRegistry().addModule(Reserved.DEFAULT_MODULE);
      Scope scope = module.getScope();
      Scope stack = scope.getChild();
      
      return new InvocationContext(cache, context, stack);
   }
   
   private static class InvocationContext {
      
      private final DecimalFormat format;
      private final InvocationCache cache;
      private final Context context;
      private final Scope scope;
 
      public InvocationContext(InvocationCache cache, Context context, Scope scope) {
         this.format = new DecimalFormat("###,###,###,###,###,###,###");
         this.context = context;
         this.scope = scope;
         this.cache = cache;
      }
      
      public void timeInvocation(int count, Object... arguments) throws Exception {
         long start = System.currentTimeMillis();
         
         for(int i = 0; i < count; i++) {
            Invocation invocation = cache.fetch(scope, arguments);
            assertNotNull(invocation);
         }
         long finish = System.currentTimeMillis();
         double duration = finish - start;
         double perMillis = count / duration;
         long perSecond = Math.round(perMillis * 1000);
         
         System.err.println(duration);
         System.err.println(format.format(perSecond) + " invocations per second -> " + Arrays.toString(arguments));
      }
   }
}
