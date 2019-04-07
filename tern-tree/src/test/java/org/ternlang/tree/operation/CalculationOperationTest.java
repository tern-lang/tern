package org.ternlang.tree.operation;

import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;
import org.ternlang.core.Context;
import org.ternlang.core.IdentityEvaluation;
import org.ternlang.core.Reserved;
import org.ternlang.core.constraint.ClassConstraint;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.TypeConstraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.EmptyModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.ModelScope;
import org.ternlang.tree.MockContext;
import org.ternlang.tree.MockType;
import org.ternlang.tree.math.NumberOperator;

public class CalculationOperationTest extends TestCase {

   public void testOperation() throws Exception {
      Context context = new MockContext();
      Module module = context.getRegistry().addModule(Reserved.DEFAULT_MODULE);

      assertPlusOperation(String.class, Date.class, String.class);
      assertPlusOperation(null, (Class)null, Double.class);
      assertPlusOperation(null, Locale.class, String.class);
      assertPlusOperation(Integer.class, null, Double.class);
      assertPlusOperation(Integer.class, Integer.class, Integer.class);
      assertPlusOperation(Byte.class, Integer.class, Integer.class);
      assertPlusOperation(Constraint.DOUBLE, new TypeConstraint(new MockType(module, "Blah", null, null)), String.class);
      assertPlusOperation(Constraint.STRING, new TypeConstraint(new MockType(module, "Blah", null, null)), String.class);
      assertPlusOperation(Constraint.NONE, new TypeConstraint(new MockType(module, "Blah", null, null)), String.class);
      assertPlusOperation(Constraint.NONE, Constraint.NONE, Double.class);
      assertPlusOperation(Constraint.NONE, Constraint.INTEGER, Double.class);
      assertPlusOperation(Constraint.INTEGER, Constraint.INTEGER, Integer.class);
      assertPlusOperation(Constraint.INTEGER, Constraint.CHARACTER, Integer.class);
      assertPlusOperation(Constraint.INTEGER, Constraint.SHORT, Integer.class);
      assertPlusOperation(Constraint.INTEGER, Constraint.BIG_INTEGER, BigInteger.class);
   }

   public void assertPlusOperation(Class left, Class right, Class expect) throws Exception {
      assertPlusOperation(new ClassConstraint(left), new ClassConstraint(right), expect);
   }

   public void assertPlusOperation(Constraint left, Constraint right, Class expect) throws Exception {
      IdentityEvaluation leftEval = new IdentityEvaluation(null, left);
      IdentityEvaluation rightEval = new IdentityEvaluation(null, right);
      CalculationOperation op = new CalculationOperation(NumberOperator.PLUS, leftEval, rightEval);
      Model model = new EmptyModel();
      Context context = new MockContext();
      Module module = context.getRegistry().addModule(Reserved.DEFAULT_MODULE);
      ModelScope scope = new ModelScope(model, module);
      Constraint result = op.compile(scope, Constraint.NONE);

      System.err.println(result);
      System.err.println(result.getType(scope));
      System.err.println(result.getType(scope).getType());
      assertEquals(result.getType(scope).getType(), expect);
   }
}
