package org.ternlang.compile;

import junit.framework.TestCase;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.compile.StoreContext;
import org.ternlang.core.Context;
import org.ternlang.core.scope.EmptyModel;
import org.ternlang.core.scope.Model;

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
