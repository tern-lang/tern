package org.ternlang.tree.reference;

import static org.ternlang.core.constraint.Constraint.OBJECT;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.parse.StringToken;

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
