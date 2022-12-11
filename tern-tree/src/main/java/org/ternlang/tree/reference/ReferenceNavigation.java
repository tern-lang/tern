package org.ternlang.tree.reference;

import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.tree.reference.ReferenceOperator.FORCE;
import static org.ternlang.core.Expansion.CLOSURE;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.PlaceHolder;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.closure.Closure;
import org.ternlang.tree.closure.ClosureParameterList;
import org.ternlang.tree.constraint.GenericList;
import org.ternlang.tree.function.ParameterDeclaration;
import org.ternlang.tree.variable.Variable;

public class ReferenceNavigation implements Compilation {

   private final StringToken operator;
   private final PlaceHolder holder;
   private final Evaluation part;
   private final Evaluation next;

   public ReferenceNavigation(Evaluation part) {
      this(part, null, null);
   }

   public ReferenceNavigation(PlaceHolder holder) {
      this(holder, null, null);
   }

   public ReferenceNavigation(PlaceHolder holder, StringToken operator, Evaluation next) {
      this.operator = operator;
      this.holder = holder;
      this.part = null;
      this.next = next;
   }

   public ReferenceNavigation(Evaluation part, StringToken operator, Evaluation next) {
      this.operator = operator;
      this.holder = null;
      this.part = part;
      this.next = next;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      if (next == null && holder != null) {
         return new Variable(holder, CLOSURE).compile(module, path, line);
      }
      if (next != null && holder != null) {
         Evaluation variable = new Variable(holder).compile(module, path, line);
         Evaluation expression = new CompileResult(variable, operator, next);
         ModifierList modifiers = new ModifierList();
         GenericList generics = new GenericArgumentList();
         AnnotationList annotations = new AnnotationList();
         ParameterDeclaration declaration = new ParameterDeclaration(annotations, modifiers, holder);
         ClosureParameterList parameters = new ClosureParameterList(declaration);
         Closure closure = new Closure(modifiers, generics, parameters, expression);

         return closure.compile(module, path, line);
      }
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