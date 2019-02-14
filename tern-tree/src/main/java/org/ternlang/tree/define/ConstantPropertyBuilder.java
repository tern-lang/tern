package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.CONSTANT;
import static org.ternlang.core.ModifierType.STATIC;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.DeclarationConstraint;
import org.ternlang.core.function.Accessor;
import org.ternlang.core.function.AccessorProperty;
import org.ternlang.core.function.ScopeAccessor;
import org.ternlang.core.function.StaticAccessor;
import org.ternlang.core.property.Property;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;

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