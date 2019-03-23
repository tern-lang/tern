package org.ternlang.tree.operation;

import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;
import org.ternlang.core.Context;
import org.ternlang.core.IdentityEvaluation;
import org.ternlang.core.Reserved;
import org.ternlang.core.constraint.ClassConstraint;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.EmptyModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.ModelScope;
import org.ternlang.tree.MockContext;
import org.ternlang.tree.math.NumberOperator;

public class CalculationOperationTest extends TestCase {

   public void testOperation() throws Exception {
      assertPlusOperation(String.class, Date.class, String.class);
      assertPlusOperation(null, null, Double.class);
      assertPlusOperation(null, Locale.class, String.class);
      assertPlusOperation(Integer.class, null, Double.class);
      assertPlusOperation(Integer.class, Integer.class, Integer.class);
      assertPlusOperation(Byte.class, Integer.class, Integer.class);

   }

   public void assertPlusOperation(Class left, Class right, Class expect) throws Exception {
      IdentityEvaluation leftEval = new IdentityEvaluation(null, new ClassConstraint(left));
      IdentityEvaluation rightEval = new IdentityEvaluation(null, new ClassConstraint(right));
      CalculationOperation op = new CalculationOperation(NumberOperator.PLUS, leftEval, rightEval);
      Model model = new EmptyModel();
      Context context = new MockContext();
      Module module = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE);
      ModelScope scope = new ModelScope(model, module);
      Constraint result = op.compile(scope, Constraint.NONE);

      System.err.println(result);
      System.err.println(result.getType(scope));
      System.err.println(result.getType(scope).getType());
      assertEquals(result.getType(scope).getType(), expect);
   }
}
