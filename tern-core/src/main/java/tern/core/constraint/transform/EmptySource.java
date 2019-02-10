package tern.core.constraint.transform;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import tern.core.constraint.Constraint;

public class EmptySource implements ConstraintSource {
   
   public EmptySource() {
      super();
   }

   @Override
   public List<Constraint> getConstraints(Constraint constraint) {
      return EMPTY_LIST;
   }

}
