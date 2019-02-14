package org.ternlang.core.type.index;

import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class GenericClassConstraint extends Constraint {
   
   private GenericConstraintResolver resolver;
   private List<Constraint> generics;
   private Object source;
   private Type type;
   private String name;
   private int modifiers;
   
   public GenericClassConstraint(GenericConstraintResolver resolver, Object source) {
      this(resolver, source, null);
   }
   
   public GenericClassConstraint(GenericConstraintResolver resolver, Object source, String name) {
      this(resolver, source, name, 0);
   }
   
   public GenericClassConstraint(GenericConstraintResolver resolver, Object source, String name, int modifiers) {
      this.modifiers = modifiers;
      this.resolver = resolver;
      this.source = source;
      this.name = name;
   }

   @Override
   public Type getType(Scope scope) {
      if(type == null) {
         Constraint constraint = resolver.resolve(source);
         
         if(constraint == null) {
            throw new InternalStateException("Could not resolve constraint");
         }
         type = constraint.getType(scope);
         generics = constraint.getGenerics(scope);
      }
      return type;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      if(generics == null) {
         Constraint constraint = resolver.resolve(source);
         
         if(constraint == null) {
            throw new InternalStateException("Could not resolve constraint");
         }
         type = constraint.getType(scope);
         generics = constraint.getGenerics(scope);
      }
      return generics;
   }
   
   @Override
   public String getName(Scope scope) {
      return name;
   }
   
   @Override
   public boolean isVariable(){
      return !ModifierType.isConstant(modifiers);
   }
   
   @Override
   public boolean isConstant(){
      return ModifierType.isConstant(modifiers);
   }
   
   @Override
   public boolean isClass(){
      return ModifierType.isClass(modifiers);
   }
   
   @Override
   public String toString(){
      return String.valueOf(type);
   }
}
