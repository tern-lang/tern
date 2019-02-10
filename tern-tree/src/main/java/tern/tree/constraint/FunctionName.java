package tern.tree.constraint;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.tree.NameReference;
import tern.tree.literal.TextLiteral;

public class FunctionName implements GenericName {

   private final NameReference reference;
   private final GenericList generics;

   public FunctionName(TextLiteral literal, GenericList generics) {
      this.reference = new NameReference(literal);
      this.generics = generics;
   }

   @Override
   public String getName(Scope scope) throws Exception{ // called from outer class
      return reference.getName(scope);
   }

   @Override
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      return generics.getGenerics(scope);
   }
}