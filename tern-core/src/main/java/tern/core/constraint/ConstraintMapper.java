package tern.core.constraint;

import java.util.ArrayList;
import java.util.List;

import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.Type;

public class ConstraintMapper {
   
   public ConstraintMapper() {
      super();
   }
   
   public Constraint map(Scope scope, String name) {    
      ScopeState state = scope.getState();
      Constraint constraint = state.getConstraint(name);
      
      if(constraint == null) {
         return new TypeParameterConstraint(null, name);
      }
      return constraint;
   }
   
   public Constraint map(Scope scope, Constraint constraint) {    
      Type type = constraint.getType(scope);
      
      if(type != null) {
         String name = constraint.getName(scope);
         Class real = type.getType();
         
         if(real == Object.class) {
            return new TypeParameterConstraint(null, name);
         }
         if(real == void.class) {
            return new TypeParameterConstraint(null, name);
         }
      }
      return constraint;
   }
   
   public List<Constraint> map(Scope scope, List<Constraint> constraints) {  
      int count = constraints.size();
      
      if(count > 0) {
         List<Constraint> matches = new ArrayList<Constraint>(count);
         
         for(Constraint constraint : constraints) {
            Constraint match = map(scope, constraint);
            matches.add(match);
         }
         return matches;
      }
      return constraints;
   }
}

