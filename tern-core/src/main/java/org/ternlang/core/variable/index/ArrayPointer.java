package org.ternlang.core.variable.index;

import static org.ternlang.core.Reserved.PROPERTY_LENGTH;
import static org.ternlang.core.Reserved.TYPE_CLASS;
import static org.ternlang.core.constraint.Constraint.INTEGER;
import static org.ternlang.core.constraint.Constraint.TYPE;

import java.lang.reflect.Array;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.core.variable.bind.VariableFinder;

public class ArrayPointer implements VariablePointer<Object> {
   
   private final TypeInstancePointer pointer;
   private final String name;
   
   public ArrayPointer(VariableFinder finder, String name) {
      this.pointer = new TypeInstancePointer(finder, name);
      this.name = name;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      if(name.equals(PROPERTY_LENGTH)) {
         return INTEGER;
      }
      if(name.equals(TYPE_CLASS)) {
         return TYPE;
      }
      return pointer.getConstraint(scope, left);
   }

   @Override
   public Value getValue(Scope scope, Object left) {
      if(name.equals(PROPERTY_LENGTH)) {
         int length = Array.getLength(left);
         return Value.getConstant(length);
      }
      return pointer.getValue(scope, left);
   }
}