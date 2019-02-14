package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.CONSTANT;
import static org.ternlang.core.ModifierType.STATIC;

import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Accessor;
import org.ternlang.core.function.AccessorProperty;
import org.ternlang.core.function.StaticAccessor;
import org.ternlang.core.property.Property;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.ArgumentList;
import org.ternlang.tree.NameReference;

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