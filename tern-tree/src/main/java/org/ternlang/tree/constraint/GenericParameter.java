package org.ternlang.tree.constraint;

import static org.ternlang.core.constraint.Constraint.OBJECT;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.TypeParameterConstraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.NameReference;
import org.ternlang.tree.literal.TextLiteral;

public class GenericParameter {

   private final NameReference reference;
   private final Constraint constraint;
   
   public GenericParameter(TextLiteral identifier) {
      this(identifier, OBJECT);
   }
   
   public GenericParameter(TextLiteral identifier, Constraint constraint) {
      this.reference = new NameReference(identifier);
      this.constraint = constraint;
   }
   
   public Constraint getGeneric(Scope scope) throws Exception {
      String parameter = reference.getName(scope);
      Type type = constraint.getType(scope);
      List<Constraint> generics = constraint.getGenerics(scope);
         
      return new TypeParameterConstraint(type, generics, parameter);
   }
}
