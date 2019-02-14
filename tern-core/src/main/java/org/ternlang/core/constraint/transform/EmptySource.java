package org.ternlang.core.constraint.transform;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.ternlang.core.constraint.Constraint;

public class EmptySource implements ConstraintSource {
   
   public EmptySource() {
      super();
   }

   @Override
   public List<Constraint> getConstraints(Constraint constraint) {
      return EMPTY_LIST;
   }

}
