package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.type.Type;

public class GenericTransform implements ConstraintTransform{
   
   private final GenericConstraintBuilder builder;
   private final ConstraintIndex index;
   
   public GenericTransform(Type type, ConstraintIndex index, ConstraintTransform[] list) {
      this.builder = new GenericConstraintBuilder(type, list);
      this.index = index;
   }
   
   @Override
   public ConstraintRule apply(Constraint left){
      Constraint constraint = builder.create(left);
      
      if(constraint == null) {
         throw new InternalStateException("No constraint for '" + left + "'");
      }
      return new ConstraintIndexRule(constraint, index);
   }
}
