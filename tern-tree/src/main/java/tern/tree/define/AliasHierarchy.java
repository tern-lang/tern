package tern.tree.define;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.constraint.ConstraintVerifier;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.tree.constraint.TypeConstraint;

public class AliasHierarchy extends ClassHierarchy {

   private final ConstraintVerifier verifier;
   private final TypeConstraint actual;

   public AliasHierarchy(TypeConstraint actual) {
      this.verifier = new ConstraintVerifier();
      this.actual = actual;
   }

   @Override
   public void define(Scope scope, Type type) throws Exception {
      List<Constraint> types = type.getTypes();
      Type match = actual.getType(scope);

      if (match == null) {
         throw new InternalStateException("Invalid alias for type '" + type + "'");
      }
      types.add(actual);
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception {
      List<Constraint> types = type.getTypes();

      for (Constraint base : types) {
         try {
            verifier.verify(scope, base);
         } catch (Exception e) {
            throw new InternalStateException("Invalid alias for type '" + type + "'", e);
         }
      }
   }
}

