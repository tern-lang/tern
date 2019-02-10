package tern.tree.reference;

import tern.core.Context;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeLoader;
import tern.core.variable.Value;
import tern.parse.StringToken;

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