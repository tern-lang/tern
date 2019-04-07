package org.ternlang.compile;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ternlang.common.Consumer;
import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.Context;
import org.ternlang.core.ExpressionEvaluator;
import org.ternlang.core.Reserved;
import org.ternlang.core.scope.MapModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.instance.Instance;

import junit.framework.TestCase;

public class EvaluatorTest extends TestCase {
   
   private static final int ITERATIONS = 1000000;
   
   private static final String SOURCE_1 =
   "import common.Consumer;\n"+
   "\n"+
   "class QuickSort with Consumer {\n"+
   "\n"+
   "    public override consume(items) {\n"+
   "      quicksort(items, 0, items.size() - 1);\n"+
   "      return items;\n"+
   "    }\n"+
   "\n"+
   "  private partition(array, left, right) {\n"+
   "      let pivot = array[(left + right) >>> 1];\n"+
   "      while (left <= right) {\n"+
   "          while (array[left] < pivot) { left++; }\n"+
   "          while (array[right] > pivot) { right--; }\n"+
   "          if (left <= right) {\n"+
   "              let temp = array[left];\n"+
   "              array[left++] = array[right];\n"+
   "              array[right--] = temp;\n"+
   "          }\n"+
   "      }\n"+
   "      return left;\n"+
   "  }\n"+
   "\n"+
   "  private quicksort(array, left, right) {\n"+
   "      let mid = partition(array, left, right);\n"+
   "      if (left < mid - 1) {\n"+
   "          quicksort(array, left, mid - 1);\n"+
   "      }\n"+
   "      if (right > mid) {\n"+
   "          quicksort(array, mid, right);\n"+
   "      }\n"+
   "  }\n"+
   "}\n"+
   "\n";
   
   private static final String SOURCE_2 =
   "func sort(items) {\n"+
   "  quicksort(items, 0, items.size() - 1);\n"+
   "  return items;\n"+
   "}\n"+
   "\n"+
   "func partition(array, left, right) {\n"+
   "  let pivot = array[(left + right) >>> 1];\n"+
   "  while (left <= right) {\n"+
   "      while (array[left] < pivot) { left++; }\n"+
   "      while (array[right] > pivot) { right--; }\n"+
   "      if (left <= right) {\n"+
   "          let temp = array[left];\n"+
   "          array[left++] = array[right];\n"+
   "          array[right--] = temp;\n"+
   "      }\n"+
   "  }\n"+
   "  return left;\n"+
   "}\n"+
   "\n"+
   "func quicksort(array, left, right) {\n"+
   "  let mid = partition(array, left, right);\n"+
   "  if (left < mid - 1) {\n"+
   "      quicksort(array, left, mid - 1);\n"+
   "  }\n"+
   "  if (right > mid) {\n"+
   "      quicksort(array, mid, right);\n"+
   "  }\n"+
   "}\n";
  

   public void testSortFunction() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      StringCompiler compiler = new StringCompiler(context);
      compiler.compile(SOURCE_2).execute(model);
      ExpressionEvaluator evaluator = context.getEvaluator();
      List<Integer> numbers = new ArrayList<Integer>();
      SecureRandom random = new SecureRandom();
      
      map.put("numbers", numbers);
      
      for(int i = 0; i < ITERATIONS; i++) {
         numbers.add(random.nextInt(ITERATIONS));
      }
      long time = System.currentTimeMillis();
      List<Integer> sorted = (List<Integer>)evaluator.evaluate(model, "sort(numbers)", Reserved.DEFAULT_MODULE);
      
//      for(Integer num : sorted) {
//         System.err.println(num);
//      }
      long finish = System.currentTimeMillis();
      long duration = finish - time;
      
      System.err.println("function sort of " + ITERATIONS + " took " + duration + " ms");
   }
   
   public void testObjectAsConsumer() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      StringCompiler compiler = new StringCompiler(context);
      compiler.compile(SOURCE_1).execute(model);
      ExpressionEvaluator evaluator = context.getEvaluator();
      Instance instance = (Instance)evaluator.evaluate(model, "new QuickSort()", Reserved.DEFAULT_MODULE);
      Consumer consumer = (Consumer)instance.getProxy();
      List<Integer> numbers = new ArrayList<Integer>();
      SecureRandom random = new SecureRandom();
      
      for(int i = 0; i < ITERATIONS; i++) {
         numbers.add(random.nextInt(ITERATIONS));
      }
      long time = System.currentTimeMillis();
      List<Integer> sorted = (List<Integer>)consumer.consume(numbers);
      
//      for(Integer num : sorted) {
//         System.err.println(num);
//      }
      long finish = System.currentTimeMillis();
      long duration = finish - time;
      
      System.err.println("object sort of " + ITERATIONS + " took " + duration + " ms");
   }
   
   public void testComparator() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      StringCompiler compiler = new StringCompiler(context);
      compiler.compile("func make(): Comparator { return (a, b) -> Integer.compare(a,b); }").execute(model);
      ExpressionEvaluator evaluator = context.getEvaluator();
      Comparator forward = (Comparator)evaluator.evaluate(model, "make()", Reserved.DEFAULT_MODULE);
      Comparator reversed = forward.reversed();
      List<Integer> numbers = new ArrayList<Integer>();
      SecureRandom random = new SecureRandom();
      
      for(int i = 0; i < ITERATIONS; i++) {
         numbers.add(random.nextInt(ITERATIONS));
      }
      long time = System.currentTimeMillis();
      
      Collections.sort(numbers, forward);
      
      long finish = System.currentTimeMillis();
      long duration = finish - time;
      
      int first = numbers.get(0);
      int last = numbers.get(ITERATIONS-1);
      
      assertTrue(first < last);
      
      System.err.println((ITERATIONS/duration) +" per ms");
   }

   public void testEvaluator() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      ExpressionEvaluator evaluator = context.getEvaluator();
      Object result = evaluator.evaluate(model, "1+2");
      assertEquals(result, 3);
   }

   public void testCompilerWithArgument() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      ExpressionEvaluator evaluator = context.getEvaluator();

      map.put("x", "blah");
      Object result = evaluator.evaluate(model,"x.substring(1)");
      assertEquals(result, "lah");
   }

   public void testSignedLiteral() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      ExpressionEvaluator evaluator = context.getEvaluator();
      Object result = evaluator.evaluate(model,"-1");
      assertEquals(result, -1);
   }

   public void testSignedLiteralCalc() throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      map.put("m", 10);
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      ExpressionEvaluator evaluator = context.getEvaluator();
      Object result = evaluator.evaluate(model,"m * -1");
      assertEquals(result, -10);
   }

}
