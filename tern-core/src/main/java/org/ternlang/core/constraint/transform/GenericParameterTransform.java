package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

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
