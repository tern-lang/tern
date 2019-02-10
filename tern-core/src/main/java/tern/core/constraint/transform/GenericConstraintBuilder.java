package tern.core.constraint.transform;

import java.util.ArrayList;
import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.constraint.TypeConstraint;
import tern.core.type.Type;

public class GenericConstraintBuilder {
   
   private final ConstraintTransform[] list;
   private final Type type;
   
   public GenericConstraintBuilder(Type type, ConstraintTransform[] list) {
      this.list = list;
      this.type = type;
   }
   
   public Constraint create(Constraint origin){
      List<Constraint> constraints = new ArrayList<Constraint>();

      for(ConstraintTransform entry : list){
         ConstraintRule rule = entry.apply(origin);
         Constraint constraint = rule.getSource();
         
         constraints.add(constraint);
      }
      return new TypeConstraint(type, constraints);
   }
}
