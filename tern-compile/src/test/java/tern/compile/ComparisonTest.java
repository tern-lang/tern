package tern.compile;

import junit.framework.TestCase;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
import tern.compile.StoreContext;
import tern.core.Context;
import tern.core.scope.EmptyModel;
import tern.core.scope.Model;

public class ComparisonTest extends TestCase {

   public void testComparisons() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Model model = new EmptyModel();
      
      assertEquals(Boolean.FALSE, context.getEvaluator().evaluate(model, "'a'>'b'"));
      assertEquals(Boolean.TRUE, context.getEvaluator().evaluate(model, "'a'<'b'"));
      assertEquals(Boolean.FALSE, context.getEvaluator().evaluate(model, "\"a\">\"b\""));
      assertEquals(Boolean.TRUE, context.getEvaluator().evaluate(model, "\"a\"<\"b\""));
   }
}
