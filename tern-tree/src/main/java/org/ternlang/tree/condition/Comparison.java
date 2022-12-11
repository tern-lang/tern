package org.ternlang.tree.condition;

import org.ternlang.core.Compilation;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Expansion;
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
import org.ternlang.tree.reference.GenericArgumentList;

import static org.ternlang.core.Reserved.PLACE_HOLDER;
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
      Expansion expansion = expansion(module, path, line);

      if(expansion.isClosure()) {
         return closure(module, path, line);
      }
      return create(module, path, line);
   }

   private Expansion expansion(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();

      if(right != null) {
         if(right.expansion(scope).isClosure()) {
            return Expansion.CLOSURE;
         }
      }
      return left.expansion(scope);
   }

   private Evaluation create(Module module, Path path, int line) {
      return new CompileResult(left, operator, right);
   }

   private Evaluation closure(Module module, Path path, int line) throws Exception {
      StringToken token = new StringToken(PLACE_HOLDER);
      PlaceHolder holder = new PlaceHolder(token);
      ModifierList modifiers = new ModifierList();
      GenericList generics = new GenericArgumentList();
      AnnotationList annotations = new AnnotationList();
      ParameterDeclaration declaration = new ParameterDeclaration(annotations, modifiers, holder);
      ClosureParameterList parameters = new ClosureParameterList(declaration);
      Evaluation body = new CompileResult(left, operator, right);
      Closure closure = new Closure(modifiers, generics, parameters, body);

      return closure.compile(module, path, line);
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