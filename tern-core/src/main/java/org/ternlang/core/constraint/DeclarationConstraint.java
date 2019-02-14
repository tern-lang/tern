package org.ternlang.core.constraint;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class DeclarationConstraint extends Constraint {
   
   private final Constraint constraint;
   private final int modifiers;
   
   public DeclarationConstraint(Constraint constraint) {
      this(constraint, 0);
   }
   
   public DeclarationConstraint(Constraint constraint, int modifiers) {      
      this.constraint = constraint;
      this.modifiers = modifiers;
   }
   
   public DeclarationConstraint getConstraint(Scope scope, int mask) {
      if(mask != modifiers) {
         return new DeclarationConstraint(constraint, mask);
      }
      return this;
   }
   
   @Override
   public List<String> getImports(Scope scope) {
      if(constraint != null) {
         return constraint.getImports(scope);
      }
      return EMPTY_LIST;
   }

   @Override
   public List<Constraint> getGenerics(Scope scope) {
      if(constraint != null) {
         return constraint.getGenerics(scope);
      }
      return EMPTY_LIST;
   }

   @Override
   public Type getType(Scope scope) {
      if(constraint != null) {
         return constraint.getType(scope);
      }
      return null;
   }

   @Override
   public String getName(Scope scope) {
      if(constraint != null) {
         return constraint.getName(scope);
      }
      return null;
   }
   
   @Override
   public boolean isVariable() {
      if(constraint != null) {
         return constraint.isVariable();
      }
      return true;
   }
   
   @Override
   public boolean isClass() {
      if(constraint != null) {
         return constraint.isClass();
      }
      return false;
   }
   
   @Override
   public boolean isModule() {
      if(constraint != null) {
         return constraint.isModule();
      }
      return false;
   }
   
   @Override
   public boolean isPrivate() {
      return ModifierType.isPrivate(modifiers);
   }

   @Override
   public boolean isConstant() {
      return ModifierType.isConstant(modifiers);
   }

   @Override
   public boolean isStatic() {
      return ModifierType.isStatic(modifiers);
   }

   @Override
   public String toString() {
      return String.valueOf(constraint);
   }
}