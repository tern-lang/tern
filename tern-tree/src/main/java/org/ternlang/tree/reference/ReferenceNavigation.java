package org.ternlang.tree.reference;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.tree.reference.ReferenceOperator.FORCE;

public class ReferenceNavigation implements Compilation {

   private final StringToken operator;
   private final Evaluation part;
   private final Evaluation next;

   public ReferenceNavigation(Evaluation part) {
      this(part, null, null);
   }

   public ReferenceNavigation(Evaluation part, StringToken operator, Evaluation next) {
      this.operator = operator;
      this.part = part;
      this.next = next;
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      if(next != null) {
         return new CompileResult(part, operator, next);
      }
      return part;
   }

   private static class CompileResult extends Evaluation {

      private final ReferenceOperator operator;
      private final Evaluation part;
      private final Evaluation next;

      public CompileResult(Evaluation part, StringToken operator, Evaluation next) {
         this.operator = ReferenceOperator.resolveOperator(operator);
         this.part = part;
         this.next = next;
      }

      @Override
      public boolean expansion(Scope scope) throws Exception {
         return part.expansion(scope) || next.expansion(scope);
      }

      @Override
      public void define(Scope scope) throws Exception {
         next.define(scope);
         part.define(scope);
      }

      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         Constraint value = part.compile(scope, left);

         if (operator == FORCE) {
            return next.compile(scope, NONE);
         }
         if (value != null) {
            return next.compile(scope, value);
         }
         return value;
      }

      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Value value = part.evaluate(scope, left);

         if (operator != null) {
            return operator.operate(scope, next, value);
         }
         return value;
      }
   }
}