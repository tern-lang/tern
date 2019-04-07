package org.ternlang.compile;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.Context;
import org.ternlang.core.Reserved;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.FunctionComparator;
import org.ternlang.core.function.ClosureFunctionFinder;
import org.ternlang.core.function.Function;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;

import junit.framework.TestCase;

public class ClosureFunctionFinderTest extends TestCase {

   private static final Map<Class, Function> CACHE = new ConcurrentHashMap<Class, Function>();
   private static final Map<Class, Object> INSTANCES = new ConcurrentHashMap<Class, Object>();
   
   private static interface StringWithPrimitiveInteger {
      void process(String s, int i);
   }
   
   private static interface InvalidAsTwoAbstractMethods extends Comparator {
      void process(String s, int i);
   }
   
   private static abstract class TwoSimilarMethods {
      public boolean process(String i, int off, long val) {
         return true;
      }
      public abstract void execute(String i, int off, long val);
   }
   
   public void testFunctionFinder() throws Exception {
      Scope scope = createContext().getRegistry().addModule(Reserved.DEFAULT_MODULE).getScope();
      
      assertNotNull(findClosure(Comparator.class));
      assertFalse(findClosure(Comparator.class).getSignature().isVariable());
      assertEquals(findClosure(Comparator.class).getSignature().getParameters().size(), 2);
      assertEquals(findClosure(Comparator.class).getSignature().getParameters().get(0).getConstraint().getType(scope).getType(), Object.class);
      assertEquals(findClosure(Comparator.class).getSignature().getParameters().get(1).getConstraint().getType(scope).getType(), Object.class);
      
      assertNotNull(findClosure(Runnable.class));
      assertFalse(findClosure(Runnable.class).getSignature().isVariable());
      assertEquals(findClosure(Runnable.class).getSignature().getParameters().size(), 0);

      assertNotNull(findClosure(StringWithPrimitiveInteger.class));
      assertFalse(findClosure(StringWithPrimitiveInteger.class).getSignature().isVariable());
      assertEquals(findClosure(StringWithPrimitiveInteger.class).getSignature().getParameters().size(), 2);
      assertEquals(findClosure(StringWithPrimitiveInteger.class).getSignature().getParameters().get(0).getConstraint().getType(scope).getType(), String.class);
      assertEquals(findClosure(StringWithPrimitiveInteger.class).getSignature().getParameters().get(1).getConstraint().getType(scope).getType(), Integer.class);
      
      assertNull(findClosure(InvalidAsTwoAbstractMethods.class));
      assertNull(findClosure(PriorityQueue.class));
      assertNull(findClosure(Map.class));
      assertNull(findClosure(Serializable.class));
      assertNull(findClosure(Externalizable.class));
      assertNull(findClosure(Iterator.class));
   
      assertNotNull(findClosure(TwoSimilarMethods.class));
      assertFalse(findClosure(TwoSimilarMethods.class).getSignature().isVariable());
      assertEquals(findClosure(TwoSimilarMethods.class).getSignature().getParameters().size(), 3);
      assertEquals(findClosure(TwoSimilarMethods.class).getSignature().getParameters().get(0).getConstraint().getType(scope).getType(), String.class);
      assertEquals(findClosure(TwoSimilarMethods.class).getSignature().getParameters().get(1).getConstraint().getType(scope).getType(), Integer.class);
      assertEquals(findClosure(TwoSimilarMethods.class).getSignature().getParameters().get(2).getConstraint().getType(scope).getType(), Long.class);
      
      assertNotNull(findClosure(Iterable.class));
      assertFalse(findClosure(Iterable.class).getSignature().isVariable());
      assertEquals(findClosure(Iterable.class).getSignature().getParameters().size(), 0);
      
      assertTrue(findClosure(Comparable.class) == findClosure(Comparable.class)); // make sure its cached and returns identical functions
      assertTrue(findClosure(Callable.class) == findClosure(Callable.class));
      
      assertNotNull(findClosure(Comparable.class));
      assertNotNull(findClosure(Callable.class));
   }
   
   private static Function findClosure(Class value) throws Exception {
      Function function = CACHE.get(value);
      
      if(function == null) {
         Context context = createContext();
         Type type = context.getLoader().loadType(value);
         ClosureFunctionFinder finder = createFinder(context);
         
         function = finder.findFunctional(type);
         
         if(function != null) {
            CACHE.put(value, function);
         }
      }
      return function;
   }
   
   private static Context createContext() {
      Context context = (Context)INSTANCES.get(Context.class);
      
      if(context == null) {
         Store store = new ClassPathStore();
         context = new StoreContext(store);
         
         INSTANCES.put(Context.class, context);
      }
      return context;
   }
   
   private static ClosureFunctionFinder createFinder(Context context) {
      ClosureFunctionFinder finder = (ClosureFunctionFinder)INSTANCES.get(ClosureFunctionFinder.class);
      
      if(finder == null) {
         TypeLoader loader = context.getLoader();
         TypeExtractor extractor = context.getExtractor();
         ConstraintMatcher matcher = context.getMatcher();
         FunctionComparator comparator = new FunctionComparator(matcher);
         finder = new ClosureFunctionFinder(comparator, extractor, loader);
         
         INSTANCES.put(ClosureFunctionFinder.class, finder);
      }
      return finder;
   }
}
