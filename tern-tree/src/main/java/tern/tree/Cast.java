package tern.tree;

import tern.core.Context;
import tern.core.Evaluation;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.core.constraint.Constraint;
import tern.core.constraint.DeclarationConstraint;
import tern.core.convert.ConstraintConverter;
import tern.core.convert.ConstraintMatcher;

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
