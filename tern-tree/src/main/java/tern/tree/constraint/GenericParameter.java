package tern.tree.constraint;

import static tern.core.constraint.Constraint.OBJECT;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.constraint.TypeParameterConstraint;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.tree.NameReference;
import tern.tree.literal.TextLiteral;

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
