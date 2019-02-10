package tern.core.type.index;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

import tern.core.constraint.ArrayConstraint;
import tern.core.constraint.ClassParameterConstraint;
import tern.core.constraint.Constraint;

public class GenericConverterResolver {
   
   private final ParameterizedTypeConverter parameter;
   private final TypeVariableConverter variable;
   private final GenericArrayTypeConverter array;
   private final WildcardTypeConverter wild;
   private final ClassConverter direct;
   
   public GenericConverterResolver(GenericConstraintResolver resolver) {
      this.parameter = new ParameterizedTypeConverter(resolver);
      this.variable = new TypeVariableConverter(resolver);
      this.array = new GenericArrayTypeConverter(resolver);
      this.wild = new WildcardTypeConverter(resolver);
      this.direct = new ClassConverter();
   }

   public GenericConverter resolve(Type type) {
      if(TypeVariable.class.isInstance(type)) {
         return variable;
      }      
      if(ParameterizedType.class.isInstance(type)) {
         return parameter;
      }
      if(WildcardType.class.isInstance(type)) {
         return wild;
      }
      if(GenericArrayType.class.isInstance(type)) {
         return array;
      }
      return direct;
   }

   private static class ClassConverter implements GenericConverter<Class>{
      
      public ClassConverter(){
         super();
      }
      
      @Override
      public Constraint convert(Class type, String name, int modifiers) {
         return new ClassParameterConstraint(type, name, modifiers);
      }
   }
   
   private static class WildcardTypeConverter implements GenericConverter<WildcardType>{
      
      private final GenericConstraintResolver resolver;
      
      public WildcardTypeConverter(GenericConstraintResolver resolver){
         this.resolver = resolver;         
      }
      
      @Override
      public Constraint convert(WildcardType type, String name, int modifiers) {
         Type[] bounds = type.getUpperBounds();
         
         if(bounds.length > 0) {
            for(int i = 0; i < bounds.length; i++) {
               Type bound = bounds[i];
               
               if(bound != null) {
                  return new GenericClassConstraint(resolver, bound);
               }              
            }     
         }
         return new ClassParameterConstraint(Object.class, name);
      }
   }
   
   private static class GenericArrayTypeConverter implements GenericConverter<GenericArrayType>{
      
      private final GenericConstraintResolver resolver;
      
      public GenericArrayTypeConverter(GenericConstraintResolver resolver){
         this.resolver = resolver;         
      }
      
      @Override
      public Constraint convert(GenericArrayType type, String name, int modifiers) {
         Type entry = type.getGenericComponentType();
         Constraint constraint = resolver.resolve(entry);
         
         return new ArrayConstraint(constraint, name, 1, modifiers);
      }
   }
   
   private static class ParameterizedTypeConverter implements GenericConverter<ParameterizedType>{
      
      private final GenericConstraintResolver resolver;
      
      public ParameterizedTypeConverter(GenericConstraintResolver resolver){
         this.resolver = resolver;         
      }
      
      @Override
      public Constraint convert(ParameterizedType type, String name, int modifiers) {
         Type[] arguments = type.getActualTypeArguments();
         Class actual = (Class)type.getRawType();
         
         if(arguments.length > 0) {
            List<Constraint> constraints = new ArrayList<Constraint>();
            
            for(int i = 0; i < arguments.length; i++) {
               Type argument = arguments[i];
               Constraint constraint = resolver.resolve(argument);
               
               constraints.add(constraint);            
            }
            return new ClassParameterConstraint(actual, constraints, name, modifiers);
         }
         return new ClassParameterConstraint(actual, name, modifiers);
      }
   }
   
   private static class TypeVariableConverter implements GenericConverter<TypeVariable>{
      
      private final GenericConstraintResolver resolver;
      
      public TypeVariableConverter(GenericConstraintResolver resolver){
         this.resolver = resolver;         
      }
      
      @Override
      public Constraint convert(TypeVariable variable, String name, int modifiers) {
         Type[] bounds = variable.getBounds();
         
         if(bounds.length > 0) {
            Object declaration = variable.getGenericDeclaration();
            
            if(Class.class.isInstance(declaration) || Method.class.isInstance(declaration)) { // must be class level declaration
               String key = variable.getName();
               
               for(int i = 0; i < bounds.length; i++) {
                  Type bound = bounds[i];
                  
                  if(bound != null) {
                     return new GenericClassConstraint(resolver, bound, key, modifiers);
                  }              
               }     
            }
         }
         return new ClassParameterConstraint(null, name, modifiers);
      }
   }
}
