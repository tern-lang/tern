package tern.core.type.index;

import java.lang.reflect.Type;

import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;

public class GenericConstraintResolver {
   
   private final GenericConverterResolver resolver;
   private final Constraint[] empty;

   public GenericConstraintResolver(){
      this.resolver = new GenericConverterResolver(this);
      this.empty = new Constraint[]{};
   }

   public Constraint resolve(Object type) {
      return resolve((Type)type);
   }
   
   public Constraint resolve(Type type) {
      return resolve(type, null, 0);
   }

   public Constraint resolve(Type type, String name, int modifiers) {
      GenericConverter converter = resolver.resolve(type);
      
      if(converter == null) {
         throw new InternalStateException("No converter for " + type);
      }
      return converter.convert(type, name, modifiers);
   }   
   
   public Constraint[] resolve(Type[] types) {      
      if(types.length > 0) {
         Constraint[] constraints = new Constraint[types.length];
         
         for(int i = 0; i < types.length; i++) {
            Type type = types[i];
            Constraint constraint = resolve(type);
            
            constraints[i] = constraint;
         }
         return constraints;
      }
      return empty;
   }   
}
