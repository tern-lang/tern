package tern.compile;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
import tern.compile.StoreContext;
import tern.core.Context;
import tern.core.ExpressionEvaluator;
import tern.core.scope.MapModel;
import tern.core.scope.Model;

public class EvaluatorTest extends TestCase {

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
