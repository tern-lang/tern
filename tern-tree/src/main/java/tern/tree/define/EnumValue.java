package tern.tree.define;

import static tern.core.ModifierType.CONSTANT;
import static tern.core.ModifierType.STATIC;

import java.util.List;

import tern.core.ModifierType;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.Accessor;
import tern.core.function.AccessorProperty;
import tern.core.function.StaticAccessor;
import tern.core.property.Property;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypeState;
import tern.tree.ArgumentList;
import tern.tree.NameReference;

public class EnumValue {
   
   private final NameReference reference; 
   private final ArgumentList arguments;
   
   public EnumValue(EnumKey key) {
      this(key, null);
   }
   
   public EnumValue(EnumKey key, ArgumentList arguments) { 
      this.reference = new NameReference(key);   
      this.arguments = arguments;
   }

   public TypeState define(TypeBody body, Type type, int index) throws Exception { 
      Scope scope = type.getScope();
      String name = reference.getName(scope);
      Constraint constraint = Constraint.getConstraint(type, CONSTANT.mask);
      List<Property> properties = type.getProperties();
      int modifiers = type.getModifiers();
      
      if(!ModifierType.isEnum(modifiers)) {
         throw new InternalStateException("Type '" + type + "' is not an enum");
      }
      Accessor accessor = new StaticAccessor(body, type, name, name);
      Property property = new AccessorProperty(name, name, type, constraint, accessor, STATIC.mask | CONSTANT.mask);
      
      properties.add(property);

      return new EnumInstance(name, arguments, index);
   }
}