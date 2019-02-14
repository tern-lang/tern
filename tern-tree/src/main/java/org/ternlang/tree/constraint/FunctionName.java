package org.ternlang.tree.constraint;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.tree.NameReference;
import org.ternlang.tree.literal.TextLiteral;

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