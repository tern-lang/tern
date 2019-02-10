package tern.tree;

import static tern.core.ModifierType.CONSTANT;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.scope.ScopeBinder;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class This extends Evaluation {

   private final ScopeBinder binder;
   
   public This(StringToken token) {
      this.binder = new ScopeBinder();
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Scope instance = binder.bind(scope, scope);
      Type type = instance.getType();
      
      return Constraint.getConstraint(type, CONSTANT.mask);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Scope instance = binder.bind(scope, scope);
      Type type = instance.getType();
      Constraint constraint = Constraint.getConstraint(type, CONSTANT.mask);
      
      return Value.getConstant(instance, constraint);
   }  
}
