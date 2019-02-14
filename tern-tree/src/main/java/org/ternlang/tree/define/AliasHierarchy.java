package org.ternlang.tree.define;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.ConstraintVerifier;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.constraint.TypeConstraint;

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

