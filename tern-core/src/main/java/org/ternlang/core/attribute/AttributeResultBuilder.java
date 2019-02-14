package org.ternlang.core.attribute;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public class AttributeResultBuilder {

   private final Attribute attribute;

   public AttributeResultBuilder(Attribute attribute) {
      this.attribute = attribute;
   }

   public AttributeResult create(Scope scope) {
      Constraint returns = attribute.getConstraint();
      List<Constraint> generics = attribute.getGenerics();
      List<Constraint> constraints = returns.getGenerics(scope);
      String name = returns.getName(scope);
      int require = constraints.size();
      int optional = generics.size();

      if(name != null || optional + require > 0) {
         return new GenericAttributeResult(attribute);
      }
      return new StaticAttributeResult(attribute);
   }
}
