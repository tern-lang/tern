package org.ternlang.core.variable.bind;

import static org.ternlang.core.scope.index.AddressType.INSTANCE;
import static org.ternlang.core.scope.index.AddressType.STATIC;

import org.ternlang.core.Entity;
import org.ternlang.core.ModifierType;
import org.ternlang.core.attribute.AttributeResult;
import org.ternlang.core.attribute.AttributeResultBinder;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.property.Property;
import org.ternlang.core.property.PropertyValue;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.variable.Value;

public class PropertyResult implements VariableResult {
   
   private final AttributeResultBinder binder;
   private final Constraint[] empty;
   private final Property property;  
   private final Entity entity;
   private final String name;
   
   public PropertyResult(Property property, Entity entity, String name){
      this.binder = new AttributeResultBinder(property);
      this.empty = new Constraint[]{};
      this.property = property;
      this.entity = entity;
      this.name = name;
   }
   
   @Override
   public Address getAddress(int offset) {
      String alias = property.getAlias();
      int modifiers = property.getModifiers();
      
      if(!ModifierType.isStatic(modifiers)) {
         return INSTANCE.getAddress(alias, offset);
      }
      return STATIC.getAddress(alias, offset);
   }
   
   @Override
   public Constraint getConstraint(Constraint left) {
      Scope scope = entity.getScope();
      AttributeResult result = binder.bind(scope);

      if(result == null) {
         throw new InternalStateException("No type for '" + property + "'");
      }
      try {
         return result.getConstraint(scope, left, empty);
      } catch(Exception e) {
         throw new InternalStateException("Invalid constraint for '" + property + "'", e);
      }
   }
   
   @Override
   public Value getValue(Object left) {
      return new PropertyValue(property, left, name);
   }
}
