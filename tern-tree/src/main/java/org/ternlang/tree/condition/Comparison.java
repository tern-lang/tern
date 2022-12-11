package org.ternlang.tree.condition;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

import static org.ternlang.core.constraint.Constraint.BOOLEAN;
import static org.ternlang.core.variable.Value.NULL;

public class Comparison implements Compilation {

   private final StringToken operator;
   private final Evaluation left;
   private final Evaluation right;
   
   public Comparison(Evaluation left) {
      this(left, null, null);
   }
   
   public Comparison(Evaluation left, StringToken operator, Evaluation right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(left, operator, right);
   }

   public static class CompileResult extends Evaluation {

      private final RelationalOperator operator;
      private final Evaluation left;
      private final Evaluation right;

      public CompileResult(Evaluation left, StringToken operator, Evaluation right) {
         this.operator = RelationalOperator.resolveOperator(operator);
         this.left = left;
         this.right = right;
      }

      @Override
      public boolean expansion(Scope scope) throws Exception {
         if (right != null) {
            return right.expansion(scope) || left.expansion(scope);
         }
         return left.expansion(scope);
      }

      @Override
      public void define(Scope scope) throws Exception {
         if (right != null) {
            right.define(scope);
         }
         left.define(scope);
      }

      @Override
      public Constraint compile(Scope scope, Constraint context) throws Exception {
         if (right != null) {
            right.compile(scope, null);
         }
         left.compile(scope, null);
         return BOOLEAN;
      }

      @Override
      public Value evaluate(Scope scope, Value context) throws Exception {
         if (right != null) {
            Value leftResult = left.evaluate(scope, NULL);
            Value rightResult = right.evaluate(scope, NULL);

            return operator.operate(scope, leftResult, rightResult);
         }
         return left.evaluate(scope, NULL);
      }
   }
}