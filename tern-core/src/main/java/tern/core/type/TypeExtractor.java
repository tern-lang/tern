package tern.core.type;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import tern.core.Handle;
import tern.core.constraint.Constraint;
import tern.core.property.Property;
import tern.core.property.PropertyExtractor;

public class TypeExtractor {
   
   private final PropertyExtractor extractor;
   private final TypeTraverser traverser;
   private final TypeLoader loader;
   
   public TypeExtractor(TypeLoader loader) {
      this.extractor = new PropertyExtractor(this);
      this.traverser = new TypeTraverser();
      this.loader = loader;
   }
   
   public Type getType(Class type) {
      if(type != null) {
         return loader.loadType(type);
      }
      return null;
   }
   
   public Type getType(Object value) {
      if(value != null) {
         Class type = value.getClass();
         
         if(Handle.class.isAssignableFrom(type)) {
            Handle handle = (Handle)value;
            Type match = handle.getHandle();
            
            if(match != null) {
               return match;
            }
         }
         return getType(type);
      }
      return null;
   }
   
   public Type getType(Type parent, String name) {
      if(parent != null) {
         return traverser.findEnclosing(parent, name);
      }
      return null;
   }
   
   public Set<Type> getTypes(Object value) {
      Type type = getType(value);
      
      if(type != null) {
         return traverser.findHierarchy(type);
      }
      return Collections.emptySet();
   }   
   
   public Set<Type> getTypes(Type type) {
      if(type != null) {
         return traverser.findHierarchy(type);
      }
      return Collections.emptySet();
   }   

   public Set<Property> getProperties(Type type) {
      if(type != null) {
         return extractor.findProperties(type);
      }
      return Collections.emptySet();
   }

   public Set<Property> getProperties(Object value) {
      Type type = getType(value);
      
      if(type != null) {
         return extractor.findProperties(type);
      }
      return Collections.emptySet();
   }   
   
   public List<Constraint> getTypes(Type type, Type base) {
      if(type != null && base != null) {
         return traverser.findPath(type, base);
      }
      return Collections.emptyList();
   }
}
