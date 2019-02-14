package org.ternlang.core.constraint;

import org.ternlang.core.ModifierType;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class StaticParameterConstraint extends Constraint {
   
   private final ConstraintDescription description;
   private final String name;
   private final Type type;
   private final int modifiers; 
   
   public StaticParameterConstraint(Type type, String name) {
      this(type, name, 0);
   }
   
   public StaticParameterConstraint(Type type, String name, int modifiers) {
      this.description = new ConstraintDescription(this, type);
      this.modifiers = modifiers;
      this.type = type;
      this.name = name;
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
