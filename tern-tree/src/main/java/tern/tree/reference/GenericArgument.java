package tern.tree.reference;

import static tern.core.constraint.Constraint.OBJECT;

import tern.core.constraint.Constraint;
import tern.parse.StringToken;

public class GenericArgument {
   
   private final Constraint constraint;
   
   public GenericArgument(StringToken token) {
      this(OBJECT, null);
   }
   
   public GenericArgument(Constraint constraint) {
      this(constraint, null);
   }
   
   public GenericArgument(Constraint constraint, StringToken token) {
      this.constraint = constraint;
   }
   
   public Constraint getConstraint() {
      return constraint;
   }

}
