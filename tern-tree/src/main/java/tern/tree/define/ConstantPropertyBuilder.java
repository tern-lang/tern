package tern.tree.define;

import static tern.core.ModifierType.CONSTANT;
import static tern.core.ModifierType.STATIC;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.constraint.DeclarationConstraint;
import tern.core.function.Accessor;
import tern.core.function.AccessorProperty;
import tern.core.function.ScopeAccessor;
import tern.core.function.StaticAccessor;
import tern.core.property.Property;
import tern.core.type.Type;
import tern.core.type.TypeBody;

public class ConstantPropertyBuilder {
   
   public ConstantPropertyBuilder() {
      super();
   }
   
   public Property createStaticProperty(TypeBody body, String name, Type type, Constraint constraint) {
      Constraint constant = new DeclarationConstraint(constraint, STATIC.mask | CONSTANT.mask);
      Accessor accessor = new StaticAccessor(body, type, name, name);
      Property property = new AccessorProperty(name, name, type, constant, accessor, STATIC.mask | CONSTANT.mask);
      
      if(type != null) {
         List<Property> properties = type.getProperties();
         properties.add(property);
      }
      return property;
   }

   public Property createInstanceProperty(String name, Type type, Constraint constraint) {
      Constraint constant = new DeclarationConstraint(constraint, STATIC.mask | CONSTANT.mask);
      Accessor accessor = new ScopeAccessor(name, name);
      Property property = new AccessorProperty(name, name, type, constant, accessor, CONSTANT.mask); // is this the correct type!!??
      
      if(type != null) {
         List<Property> properties = type.getProperties();
         properties.add(property);
      }
      return property;
   }
}