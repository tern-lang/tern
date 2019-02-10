package tern.core.constraint;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import tern.core.ModifierType;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class TypeParameterConstraint extends Constraint {
   
   private final ConstraintDescription description;
   private final List<Constraint> generics;
   private final String name;
   private final Type type;
   private final int modifiers; 
   
   public TypeParameterConstraint(Type type, String name) {
      this(type, EMPTY_LIST, name);
   }
   
   public TypeParameterConstraint(Type type, String name, int modifiers) {
      this(type, EMPTY_LIST, name, modifiers);
   }
   
   public TypeParameterConstraint(Type type, List<Constraint> generics, String name) {
      this(type, generics, name, 0);
   }
   
   public TypeParameterConstraint(Type type, List<Constraint> generics, String name, int modifiers) {
      this.description = new ConstraintDescription(this, type);
      this.modifiers = modifiers;
      this.generics = generics;
      this.type = type;
      this.name = name;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      return generics;
   }

   @Override
   public Type getType(Scope scope) {
      return type;
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
   public String toString() {
      return description.toString();
   }
}
