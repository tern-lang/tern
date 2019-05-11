package org.ternlang.core.constraint;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class StaticConstraint extends Constraint {
   
   private final ConstraintDescription description;
   private final Type type;
   private final int modifiers;
   
   public StaticConstraint(Type type) {
      this(type, 0);
   }
   
   public StaticConstraint(Type type, int modifiers) {
      this.description = new ConstraintDescription(this, type);
      this.modifiers = modifiers;
      this.type = type;
   }

   @Override
   public List<Constraint> getGenerics(Scope scope) {
      return type.getGenerics();
   } 
   
   @Override
   public Type getType(Scope scope) {
      return type;
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
