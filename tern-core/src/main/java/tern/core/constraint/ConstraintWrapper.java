package tern.core.constraint;

import static java.util.Collections.EMPTY_LIST;
import static tern.core.ModifierType.CLASS;

import tern.core.module.Module;
import tern.core.type.Type;
import tern.core.variable.Value;

public class ConstraintWrapper {
   
   public ConstraintWrapper() {
      super();
   }
   
   public Constraint toConstraint(Object value) {
      return toConstraint(value, null);
   }

   public Constraint toConstraint(Object value, String name) {  
      if(value != null) {
         if(Type.class.isInstance(value)) {
            return new TypeParameterConstraint((Type)value, EMPTY_LIST, name, CLASS.mask);
         }
         if(Module.class.isInstance(value)) {
            return new ModuleConstraint((Module)value);
         }
         if(Value.class.isInstance(value)) {         
            return new ValueConstraint((Value)value);
         } 
         if(Constraint.class.isInstance(value)) {
            return (Constraint)value;
         }
      }
      return new ObjectConstraint(value);
   }
}
