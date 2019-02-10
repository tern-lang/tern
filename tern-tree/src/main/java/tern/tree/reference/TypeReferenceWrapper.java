package tern.tree.reference;

import tern.core.Entity;
import tern.core.NameFormatter;
import tern.core.constraint.Constraint;
import tern.core.constraint.ConstraintWrapper;
import tern.core.scope.Scope;
import tern.core.scope.index.Local;
import tern.core.type.Type;
import tern.core.variable.Value;

public class TypeReferenceWrapper {

   private final ConstraintWrapper mapper;
   private final NameFormatter formatter;

   public TypeReferenceWrapper() {
      this.mapper = new ConstraintWrapper();
      this.formatter = new NameFormatter();
   }

   public Value toValue(Scope scope, Constraint type, String name) throws Exception {
      return Local.getConstant(type, name, type);
   }

   public Value toValue(Scope scope, Entity entity, String name) throws Exception {
      Constraint constraint = mapper.toConstraint(entity);

      if(name != null) {
         Type type = constraint.getType(scope);
         String defined = type.getName();
         String actual = formatter.formatInnerName(defined);

         if(!name.equals(actual)) {
            Constraint parameter = mapper.toConstraint(entity, name);
            return Local.getConstant(entity, name, parameter);
         }
      }
      return Local.getConstant(entity, null, constraint);
   }
}
