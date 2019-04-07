package org.ternlang.tree.condition;

import junit.framework.TestCase;
import org.ternlang.core.Context;
import org.ternlang.core.IdentityEvaluation;
import org.ternlang.core.Reserved;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.EmptyModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.ModelScope;
import org.ternlang.core.type.TypeLoader;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.MockContext;

public class ComparisonTest extends TestCase {

   private Context context = new MockContext();

   public void testComparisons() throws Exception {
      TypeLoader loader = context.getLoader();

      assertComparison("blah", "==", "blah", true);
      assertComparison("blah", "!=", "blah", false);
      assertComparison("a", "==", "b", false);
      assertComparison(1L, "==", 1, true);
      assertComparison(1, "==", 1L, true);
      assertComparison(1, "==", "1L", false);
      assertComparison(1, "!=", "1L", true);
      assertComparison("foo", "===", "foo", true);
      assertComparison("foo", ">=", "foo", true);
      assertComparison(100, ">=", 22.1d, true);
      assertComparison(100, ">", 22.1d, true);
      assertComparison(100, "<=", 22.1d, false);
      assertComparison(100, "<", 22.1d, false);
      assertComparison(11.4d, "<", 200, true);
      assertComparison(11.4d, "<=", 200, true);
      assertComparison("foo", "!==", new String("foo"), true);
      assertComparison("foo", "!==", "foo", false);
      assertComparison("foo", "instanceof", loader.loadType(String.class), true);
      assertComparison("foo", "instanceof", loader.loadType(Object.class), true);
      assertComparison("foo", "instanceof", loader.loadType(Integer.class), false);
      assertComparison(11, "instanceof", loader.loadType(Integer.class), true);
      assertComparison(11, "instanceof", loader.loadType(Number.class), true);
      assertComparison(11, "!instanceof", loader.loadType(Number.class), false);
   }

   private void assertComparison(Object left, String operation, Object right, boolean result) throws Exception {
      IdentityEvaluation leftEval = new IdentityEvaluation(left);
      IdentityEvaluation rightEval = new IdentityEvaluation(right);
      Comparison comparison = new Comparison(leftEval, new StringToken(operation), rightEval);
      Model model = new EmptyModel();
      Module module = context.getRegistry().addModule(Reserved.DEFAULT_MODULE);
      ModelScope scope = new ModelScope(model, module);

      assertEquals(comparison.evaluate(scope, Value.NULL).getValue(), result ? Boolean.TRUE : Boolean.FALSE);
   }
}
