package tern.core.type.index;

import static tern.core.ModifierType.ABSTRACT;
import static tern.core.ModifierType.OVERRIDE;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import tern.core.ModifierType;
import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.AccessorProperty;
import tern.core.property.ConstantProperty;
import tern.core.property.Property;

public class PropertyGenerator {
   
   private static final int MODIFIERS = OVERRIDE.mask | ABSTRACT.mask;
  
   public PropertyGenerator(){
      super();
   }
   
   public Property generate(Type type, Constraint constraint, String name, int modifiers) {
      try {
         return new ConstantProperty(name, type, constraint, type, modifiers); 
      } catch(Exception e) {
         throw new InternalStateException("Could not create property from " + type);
      }
   }
   
   public Property generate(Field field, Type type, Constraint constraint, String name, int modifiers) {
      try {
         if(ModifierType.isConstant(modifiers)) {
            FinalFieldAccessor accessor = new FinalFieldAccessor(field);
            
            if(!field.isAccessible()) {
               field.setAccessible(true);
            }
            return new AccessorProperty(name, name, type, constraint, accessor, modifiers); 
         }
         FieldAccessor accessor = new FieldAccessor(field);
         
         if(!field.isAccessible()) {
            field.setAccessible(true);
         }
         return new AccessorProperty(name, name, type, constraint, accessor, modifiers); 
      } catch(Exception e) {
         throw new InternalStateException("Could not create property from " + field);
      }
   }
   
   public Property generate(Method read, Method write, Type type, Constraint constraint, String name, int modifiers) {
      try {
         MethodAccessor accessor = new MethodAccessor(type, read, write);
         
         if(read.isAccessible()) {
            read.setAccessible(true);
         }
         if(write != null) {
            if(!write.isAccessible()) {
               write.setAccessible(true);
            }
         }
         return new AccessorProperty(name, name, type, constraint, accessor, modifiers & ~MODIFIERS);  
      } catch(Exception e) {
         throw new InternalStateException("Could not create property from " + read);
      }
   }
}