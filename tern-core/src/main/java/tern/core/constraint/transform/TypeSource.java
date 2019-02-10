package tern.core.constraint.transform;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class TypeSource implements ConstraintSource {

   private final Type type;

   public TypeSource(Type type) {
      this.type = type;
   }

   @Override
   public List<Constraint> getConstraints(Constraint constraint) {
      Scope scope = type.getScope();
      List<Constraint> generics = constraint.getGenerics(scope);
      List<Constraint> constraints = type.getGenerics();
      int require = constraints.size();
      int actual = generics.size();

      if(actual > 0) { // the generics were declared
         if(require != actual) {
            throw new InternalStateException("Invalid generic parameters for " + type);
         }
         return generics;
      }
      return constraints; // no generics declared
   }
}
