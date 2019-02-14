package org.ternlang.tree;

import static org.ternlang.core.ModifierType.CONSTANT;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeBinder;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

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
