package org.ternlang.tree;

import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.DeclarationConstraint;
import org.ternlang.core.convert.ConstraintConverter;
import org.ternlang.core.convert.ConstraintMatcher;

public class Cast extends Evaluation {

   private final Constraint constraint;
   private final Evaluation evaluation;
   
   public Cast(Evaluation evaluation, Constraint constraint) {
      this.constraint = new DeclarationConstraint(constraint);
      this.evaluation = evaluation;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) {
      return constraint;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Value value = evaluation.evaluate(scope, left);
      Type type = constraint.getType(scope);
      Object object = value.getValue();
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();
      ConstraintConverter converter = matcher.match(type);
      Object result = converter.convert(object);
      
      return Value.getTransient(result, constraint);
   }
}
