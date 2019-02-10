package tern.core.variable.bind;

import static tern.core.scope.index.AddressType.INSTANCE;
import static tern.core.scope.index.AddressType.STATIC;

import tern.core.Entity;
import tern.core.ModifierType;
import tern.core.attribute.AttributeResult;
import tern.core.attribute.AttributeResultBinder;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.property.Property;
import tern.core.property.PropertyValue;
import tern.core.scope.Scope;
import tern.core.scope.index.Address;
import tern.core.type.Type;
import tern.core.variable.Value;

public class PropertyResult implements VariableResult {
   
   private final AttributeResultBinder binder;
   private final Property property;  
   private final Entity entity;
   private final String name;
   private final Type[] empty;
   
   public PropertyResult(Property property, Entity entity, String name){
      this.binder = new AttributeResultBinder(property);
      this.empty = new Type[]{};
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
