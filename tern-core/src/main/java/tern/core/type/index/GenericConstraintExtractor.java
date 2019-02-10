package tern.core.type.index;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;

public class GenericConstraintExtractor {
   
   private final GenericConstraintResolver resolver;
   
   public GenericConstraintExtractor(){
      this.resolver = new GenericConstraintResolver();
   }

   public Constraint extractField(Field field,int modifiers) {
      Type type = field.getGenericType();
      
      try {
         return resolver.resolve(type, null, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create constraint for " + field, e);
      }
   }
   
   public Constraint[] extractGenerics(Method method) {
      Type[] generics = method.getTypeParameters();
      
      try {
         return resolver.resolve(generics);
      } catch(Exception e) {
         throw new InternalStateException("Could not create constraint for " + method, e);
      }
   }
   
   public Constraint extractReturn(Method method,int modifiers) {
      Type type = method.getGenericReturnType();
      
      try {
         return resolver.resolve(type, null, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create constraint for " + method, e);
      }
   }
   
   public Constraint[] extractParameters(Method method) {
      Type[] parameters = method.getGenericParameterTypes();
      
      try {
         return resolver.resolve(parameters);
      } catch(Exception e) {
         throw new InternalStateException("Could not create parameter constraints for " + method, e);
      }
   }
   
   public Constraint[] extractParameters(Constructor constructor) {
      Type[] parameters = constructor.getGenericParameterTypes();
      
      try {
         return resolver.resolve(parameters);
      } catch(Exception e) {
         throw new InternalStateException("Could not create parameter constraints for " + constructor, e);
      }
   }
}
