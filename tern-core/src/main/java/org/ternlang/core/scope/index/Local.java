package org.ternlang.core.scope.index;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.variable.Value;

public abstract class Local extends Value {

   public static Local getConstant(Object value, String name) {
      return new LocalConstant(value, name, NONE);
   }

   public static Local getConstant(Object value, String name, Constraint type) {
      return new LocalConstant(value, name, type);
   }

   public static Local getReference(Object value, String name) {
      return new LocalReference(value, name, NONE);
   }
   
   public static Local getReference(Object value, String name, Constraint type) {
      return new LocalReference(value, name, type);
   }
}