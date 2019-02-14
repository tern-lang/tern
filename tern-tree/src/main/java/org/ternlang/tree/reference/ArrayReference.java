package org.ternlang.tree.reference;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeLoader;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

public class ArrayReference extends ConstraintReference {

   private final Constraint constraint;
   private final StringToken[] bounds;
   
   public ArrayReference(Constraint constraint, StringToken... bounds) {
      this.constraint = constraint;
      this.bounds = bounds;
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type entry = constraint.getType(scope);
         Module module = entry.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         String prefix = module.getName();
         String name = entry.getName();         
         Type array = loader.loadArrayType(prefix, name, bounds.length);
         Constraint constraint = Constraint.getConstraint(array);
         Value reference = Value.getReference(array);
         
         return new ConstraintValue(constraint, reference, array);
      } catch(Exception e) {
         throw new InternalStateException("Invalid array constraint", e);
      }
   }
}