package tern.core.constraint.transform;

import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class GenericParameterTransform implements ConstraintTransform {
   
   private final ConstraintIndex index;
   private final Constraint next;
   private final Type type;
   
   public GenericParameterTransform(ConstraintIndex index, Constraint next, Type type){
      this.index = index;
      this.next = next;
      this.type = type;
   }
   
   @Override
   public ConstraintRule apply(Constraint left){
      Scope scope = type.getScope();
      Constraint constraint = index.update(scope, left, next);
      
      if(constraint == null) {
         throw new InternalStateException("No constraint for '" + left + "'");
      }
      return new ConstraintIndexRule(constraint, index);
   }
}
